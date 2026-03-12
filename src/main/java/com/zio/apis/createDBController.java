package com.zio.apis;

import com.zio.data.dto.IngredDTO;
import com.zio.data.dto.MealDTO;
import com.zio.data.dto.RecipeDTO;
import com.zio.service.CreationService;
import com.zio.util.ZioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("createdb")
/***
 * controller for creation of resources, ensure user has auth to access
 */
public class createDBController {

    @Autowired
    CreationService creationService;

    /// ///////////// one shot creators to init db

    @PostMapping("meals")
    public ResponseEntity<String> addMeals(@RequestBody List<ArrayList<Long>> mealList) throws ZioException {
        creationService.makeMeals(mealList);
        return ResponseEntity.ok("done all");
    }

    @PostMapping("recipes")
    public ResponseEntity<String> addRecipes(@RequestBody List<RecipeDTO> recipe) throws ZioException {
        creationService.makeRecipes(recipe);
        return ResponseEntity.ok("done");
    }

    @PostMapping("ingred")
    public ResponseEntity<String> addIngred(@RequestBody IngredDTO ingred) {
        return ResponseEntity.ok("done" + creationService.makeIngred(ingred));
    }

    @PostMapping("ingreds")
    public ResponseEntity<String> addIngreds(@RequestBody List<IngredDTO> ingreds) {
        creationService.makeIngreds(ingreds);
        return ResponseEntity.ok("done all");
    }

}
