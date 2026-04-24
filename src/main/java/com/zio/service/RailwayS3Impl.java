package com.zio.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.List;

@Service
public class RailwayS3Impl implements S3Storage {

    private final S3Client s3;
    private final AwsBasicCredentials creds;
    private final S3Presigner presigner;
    private final String BUCKET, S3URL;

    public RailwayS3Impl(@Value("${s3.keyid}")
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
                .region(Region.of("auto"))
                .endpointOverride(URI.create(S3URL))
                .build();
        presigner = S3Presigner.builder()
                .endpointOverride(URI.create(S3URL))
                .region(Region.of("auto"))
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .build();
    }

    @Override
    public String getRecipeImgUploadUrl(String recipeImgName, String mimeType) {
        String extension = "." + mimeType.substring(mimeType.lastIndexOf('/') + 1);
        return generateUploadUrl("recipeImages/" + recipeImgName + extension, mimeType);
    }

    @Override
    public String getImgDownloadUrl(String imgUrl) {
        return generateDownloadUrl(imgUrl);
    }

    @Override
    public int uploadIngredImage(MultipartFile image) throws IOException {
        return uploadImage("ingredImages/" + image.getOriginalFilename(), image);
    }

    @Override
    public List<String> getDBContents() {
        return getAllFiles().stream().map(S3Object::key).toList();
    }

    private String generateDownloadUrl(String key) {

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(BUCKET)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(60))
                .getObjectRequest(getObjectRequest)
                .build();

        return presigner
                .presignGetObject(presignRequest)
                .url()
                .toString();
    }

    private String generateUploadUrl(String key, String mimeType) {

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(key)
                .contentType(mimeType) // enforce MIME type
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(60))
                .putObjectRequest(objectRequest)
                .build();

        PresignedPutObjectRequest request = presigner.presignPutObject(presignRequest);
        return request.url().toString();
    }

    private int uploadImage(String key, MultipartFile image) throws IOException {
        RequestBody file = RequestBody.fromInputStream(image.getInputStream(), image.getSize());
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(key)
                .contentType(image.getContentType())
                .build();
        PutObjectResponse response = s3.putObject(request, file);
        return response.sdkHttpResponse().statusCode();
    }

    private List<S3Object> getAllFiles() {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(BUCKET)
                .build();
        ListObjectsV2Response response = s3.listObjectsV2(request);
        return response.contents();
    }

}
