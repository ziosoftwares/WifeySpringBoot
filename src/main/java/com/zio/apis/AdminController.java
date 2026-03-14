package com.zio.apis;

import com.zio.data.dto.IngredDTO;
import com.zio.data.dto.RecipeDTO;
import com.zio.service.CreationService;
import com.zio.service.S3Storage;
import com.zio.util.ZioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("admin")
/***
 * controller for creation of resources, ensure user has auth to access
 */
public class AdminController {

    @Autowired
    CreationService creationService;
    @Autowired
    S3Storage s3Storage;

    /// ///////////// one shot creators to init db

    @PostMapping("meals")
    public ResponseEntity<Void> addMeals(@RequestBody List<ArrayList<Long>> mealList) throws ZioException {
        for (ArrayList<Long> ids : mealList) creationService.makeMeal(ids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("recipes")
    public ResponseEntity<Void> addRecipes(@RequestBody List<RecipeDTO> recipes) throws ZioException {
        for (var recipe : recipes) creationService.makeRecipe(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("ingreds")
    public ResponseEntity<Void> addIngreds(@RequestBody List<IngredDTO> ingreds) {
        ingreds.forEach(creationService::makeIngred);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("ingredImages")
    public List<Integer> uploadImages(@RequestParam("images") List<MultipartFile> files) throws IOException {
        List<Integer> result = new ArrayList<>();
        for (var file : files)
            result.add(s3Storage.uploadIngredImage(file));
        return result;
    }

    @GetMapping("s3contents")
    public List<String> getDbContents() {
        return s3Storage.getDBContents();
    }

}
