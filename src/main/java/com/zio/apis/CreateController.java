package com.zio.apis;

import com.zio.data.api.Error;
import com.zio.data.api.Response;
import com.zio.data.dto.GeneralDTO;
import com.zio.util.ZioException;
import com.zio.data.dto.IngredDTO;
import com.zio.data.dto.MealDTO;
import com.zio.data.dto.RecipeDTO;
import com.zio.service.CreationService;
import com.zio.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("create")
/***
 * controller for creation of resources, ensure user has auth to access
 */
public class CreateController {

    @Autowired
    CreationService creationService;

    @Autowired
    PlanService planService;

    @PostMapping("meal")
    public ResponseEntity<GeneralDTO> addMeal(@RequestBody ArrayList<Long> ids) throws ZioException {
        return ResponseEntity.ok(creationService.checkMeal(ids));
    }

    @PostMapping("recipe")
    public Long addRecipe(@RequestBody RecipeDTO recipe) throws ZioException {
        return creationService.makeRecipe(recipe);
    }

    @PostMapping("recipeImage")
    public Boolean addRecipePicture(@RequestParam("image") MultipartFile image, @RequestParam("recipeId") Long recipeId) throws ZioException {
        return creationService.addRecipePicture(image, recipeId);
    }
}
