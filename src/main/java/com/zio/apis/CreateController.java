package com.zio.apis;

import com.zio.data.api.Response;
import com.zio.util.ZioException;
import com.zio.data.dto.IngredDTO;
import com.zio.data.dto.MealDTO;
import com.zio.data.dto.RecipeDTO;
import com.zio.service.CreationService;
import com.zio.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> addMeal(@RequestBody MealDTO meal) throws ZioException {
        return ResponseEntity.ok("done" + creationService.makeMeal(meal));
    }

    @PostMapping("meals")
    public ResponseEntity<String> addMeals(@RequestBody List<MealDTO> meal) {
        creationService.makeMeals(meal);
        return ResponseEntity.ok("done all");
    }

    @PostMapping("recipe")
    public Response<Long> addRecipe(@RequestBody RecipeDTO recipe) {
        return Response.ok(creationService.makeRecipe(recipe));
    }

    @PostMapping("recipes")
    public ResponseEntity<String> addRecipes(@RequestBody List<RecipeDTO> recipe) {
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
