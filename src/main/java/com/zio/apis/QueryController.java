package com.zio.apis;

import com.zio.data.api.Response;
import com.zio.data.dto.*;
import com.zio.service.QueryService;
import com.zio.util.ZioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("query")
/***
 * controller only for simple retrievals from db
 */
public class QueryController {

    @Value("${content.recipe}")
    private String recipeDir;

    @Autowired
    QueryService queryService;

    @GetMapping("ingreds/{name}")
    ResponseEntity<List<GeneralDTO>> getIngredsLike(@PathVariable String name) {
        return ResponseEntity.ok(queryService.getIngredsLike(name));
    }

    @GetMapping("ingred/{id}")
    Response<IngredDTO> getIngred(@PathVariable Long id) throws ZioException {
        return Response.ok(queryService.getIngred(id));
    }

    @GetMapping("ingreds")
    ResponseEntity<List<IngredDTO>> getIngreds(@RequestParam("ids") List<Long> ids) throws ZioException {
        return ResponseEntity.ok(queryService.getIngreds(ids));
    }

    @GetMapping("ingreds-of/meals")
    Response<List<IngredQuantityDTO>> getIngredsOfMeals(@RequestParam("ids") List<Long> mealIds) {
        return Response.ok(queryService.getIngredsOfMeals(mealIds));
    }

    @GetMapping("recipes/{name}")
    ResponseEntity<List<GeneralDTO>> getRecipeLike(@PathVariable String name) {
        return ResponseEntity.ok(queryService.getRecipeLike(name));
    }

    @GetMapping("recipe/{id}")
    Response<RecipeFullDTO> getRecipe(@PathVariable Long id) throws ZioException {
        return Response.ok(queryService.getRecipe(id));
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
