package com.zio.recipe.controller;

import com.zio.data.api.Response;
import com.zio.data.dto.*;
import com.zio.recipe.data.InstructionDTO;
import com.zio.recipe.data.RecipeDTO;
import com.zio.recipe.data.RecipeDetailsDTO;
import com.zio.recipe.service.RecipeQueryService;
import com.zio.util.ZioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("query")
/***
 * controller only for simple retrievals from db
 */
public class RecipeQueryController {


    @Autowired
    RecipeQueryService queryService;

    @GetMapping("recipes/{name}")
    ResponseEntity<List<GeneralDTO>> getRecipeLike(@PathVariable String name) {
        return ResponseEntity.ok(queryService.getRecipeLike(name));
    }

    @GetMapping("recipe/{id}")
    ResponseEntity<RecipeDTO> getRecipe(@PathVariable Long id) throws ZioException {
        return ResponseEntity.ok(queryService.getRecipe(id));
    }

    @GetMapping("recipeDetails/{id}")
    ResponseEntity<List<InstructionDTO>> getRecipeInstruction(@PathVariable Long id) throws ZioException {
        return ResponseEntity.ok(queryService.getRecipeInstruction(id));
    }

    @GetMapping("meals/{name}")
    ResponseEntity<List<GeneralDTO>> getMealsLike(@PathVariable String name) {
        return ResponseEntity.ok(queryService.getMealsLike(name));
    }

    @GetMapping("meal/{id}")
    ResponseEntity<MealFullDTO> getMealById(@PathVariable Long id) throws ZioException {
        return ResponseEntity.ok(queryService.getMealById(id));
    }


}
