package com.zio.apis;

import com.zio.data.dto.*;
import com.zio.data.entity.IngredQuantity;
import com.zio.data.entity.Meal;
import com.zio.service.QueryService;
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
public class QueryController {

    @Autowired
    QueryService queryService;

    @GetMapping("ingreds/{name}")
    ResponseEntity<List<IdNameDTO>> getIngredsLike(@PathVariable String name) {
        return ResponseEntity.ok(queryService.getIngredsLike(name));
    }

    @GetMapping("ingred/{id}")
    ResponseEntity<IngredDTO> getIngredsLike(@PathVariable Long id) throws ZioException {
        return ResponseEntity.ok(queryService.getIngred(id));
    }

    @GetMapping("ingreds")
    ResponseEntity<List<IngredDTO>> getIngreds(@RequestParam("ids") List<Long> ids) throws ZioException {
        return ResponseEntity.ok(queryService.getIngreds(ids));
    }

    @GetMapping("ingreds-of/meals")
    ResponseEntity<List<IngredQuantityDTO>> getIngredsOfMeals(@RequestParam("ids") List<Long> mealIds) {
        return ResponseEntity.ok(queryService.getIngredsOfMeals(mealIds));
    }

    @GetMapping("recipes/{name}")
    ResponseEntity<List<IdNameDTO>> getRecipeLike(@PathVariable String name) {
        return ResponseEntity.ok(queryService.getRecipeLike(name));
    }

    @GetMapping("recipe/{id}")
    ResponseEntity<RecipeFullDTO> getRecipe(@PathVariable Long id) throws ZioException {
        return ResponseEntity.ok(queryService.getRecipe(id));
    }

    @GetMapping("meals/{name}")
    ResponseEntity<List<IdNameDTO>> getMealsLike(@PathVariable String name) {
        return ResponseEntity.ok(queryService.getMealsLike(name));
    }

    @GetMapping("meal/{id}")
    ResponseEntity<MealFullDTO> getMealById(@PathVariable Long id) throws ZioException {
        return ResponseEntity.ok(queryService.getMealById(id));
    }

}
