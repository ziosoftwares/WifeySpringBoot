package com.zio.recipe.controller;

import com.zio.common.service.S3Storage;
import com.zio.common.util.ZioException;
import com.zio.recipe.data.RecipeDTO;
import com.zio.recipe.service.CreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("create")
/***
 * controller for creation of resources, ensure user has auth to access
 */
public class CreateController {

    @Autowired
    CreationService creationService;

    @Autowired
    S3Storage s3Storage;

    @PostMapping("recipe")
    public Long addRecipe(@RequestBody RecipeDTO recipe) throws ZioException {
        return creationService.makeRecipe(recipe);
    }

    @PutMapping("recipeImage")
    public String getRecipeImgUploadUrl(@RequestParam Long recipeId, @RequestParam String mimeType) throws ZioException {
        creationService.updateRecipeImg(mimeType, recipeId);
        return s3Storage.getRecipeImgUploadUrl("recipe" + recipeId, mimeType);
    }

    @PostMapping("recipeImage")
    public Boolean addRecipePicture(@RequestParam("image") MultipartFile image, @RequestParam("recipeId") Long recipeId) throws ZioException {
        return creationService.addRecipePicture(image, recipeId);
    }

}
