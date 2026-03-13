package com.zio.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URI;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

@Service
public class RailwayS3 implements S3Storage {

    private final S3Client s3;
    private final AwsBasicCredentials creds;
    private final S3Presigner presigner;
    private final String BUCKET, S3URL;

    public RailwayS3(@Value("${s3.keyid}")
                     String ACCESS_KEY_ID,
                     @Value("${s3.key}")
                     String SECRET_ACCESS_KEY,
                     @Value("${s3.bucket}")
                     String BUCKET,
                     @Value("${s3.url}")
                     String S3URL) {
        this.BUCKET = BUCKET;
        this.S3URL = S3URL;
        creds = AwsBasicCredentials.create(ACCESS_KEY_ID, SECRET_ACCESS_KEY);
        s3 = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create(S3URL))
                .build();
        presigner = S3Presigner.builder()
                .endpointOverride(URI.create(S3URL))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .build();
    }

    private String generateDownloadUrl(String key) {

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(BUCKET)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(30))
                .getObjectRequest(getObjectRequest)
                .build();

        return presigner
                .presignGetObject(presignRequest)
                .url()
                .toString();
    }

    private String generateUploadUrl(String key) {

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(key)
                //.contentType("image/*") // enforce MIME type
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(60)) // expires in 10 min
                .putObjectRequest(objectRequest)
                .build();

        return presigner.presignPutObject(presignRequest).url().toString();
    }

    private int uploadImage(String key, Path file) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(key)
                .contentType("image/*")
                .build();
        PutObjectResponse response = s3.putObject(request, RequestBody.fromFile(file));
        return response.sdkHttpResponse().statusCode();
    }

    private List<S3Object> getAllFiles() {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(BUCKET)
                .build();
        ListObjectsV2Response response = s3.listObjectsV2(request);
        return response.contents();
    }

    @Override
    public String getRecipeImgUploadUrl(String recipeImgName) {
        return generateUploadUrl("recipeImages/" + recipeImgName);
    }

    @Override
    public String getImgDownloadUrl(String recipeImgUrl) {
        return generateDownloadUrl(recipeImgUrl);
    }
}
