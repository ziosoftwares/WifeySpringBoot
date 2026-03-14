package com.zio.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3Storage {

    String getRecipeImgUploadUrl(String recipeImgName, String mimeType);

    String getImgDownloadUrl(String imgUrl);

    int uploadIngredImage(MultipartFile image) throws IOException;

    List<String> getDBContents();
}
