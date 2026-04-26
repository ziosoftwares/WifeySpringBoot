package com.zio.recipe.controller;

import com.zio.common.data.dto.GeneralDTO;
import com.zio.recipe.data.InstructionDTO;
import com.zio.recipe.data.RecipeDTO;
import com.zio.recipe.service.RecipeQueryService;
import com.zio.common.util.ZioException;
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


}
