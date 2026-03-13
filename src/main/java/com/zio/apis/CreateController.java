package com.zio.apis;

import com.zio.service.RailwayS3;
import com.zio.data.dto.GeneralDTO;
import com.zio.service.S3Storage;
import com.zio.util.ZioException;
import com.zio.data.dto.RecipeDTO;
import com.zio.service.CreationService;
import com.zio.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.internal.util.Mimetype;

import java.util.ArrayList;

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

    @PostMapping("meal")
    public ResponseEntity<GeneralDTO> addMeal(@RequestBody ArrayList<Long> ids) throws ZioException {
        return ResponseEntity.ok(creationService.checkMeal(ids));
    }

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
