package com.zio.service;

public interface S3Storage {

    String getRecipeImgUploadUrl(String recipeImgName);

    String getImgDownloadUrl(String recipeImgUrl);
}
