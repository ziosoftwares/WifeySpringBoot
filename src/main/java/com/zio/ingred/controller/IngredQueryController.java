package com.zio.ingred.controller;

import com.zio.common.data.api.Response;
import com.zio.common.data.GeneralDTO;
import com.zio.ingred.data.IngredDTO;
import com.zio.ingred.service.IngredQueryService;
import com.zio.recipe.data.IngredQuantityDTO;
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
public class IngredQueryController {


    @Autowired
    IngredQueryService queryService;

    @GetMapping("ingreds/{name}")
    ResponseEntity<List<GeneralDTO>> getIngredsLike(@PathVariable String name) {
        return ResponseEntity.ok(queryService.getIngredsLike(name));
    }

    @GetMapping("ingred/{id}")
    ResponseEntity<IngredDTO> getIngred(@PathVariable Long id) throws ZioException {
        return ResponseEntity.ok(queryService.getIngred(id));
    }

    @GetMapping("ingreds")
    ResponseEntity<List<IngredDTO>> getIngreds(@RequestParam("ids") List<Long> ids) throws ZioException {
        return ResponseEntity.ok(queryService.getIngreds(ids));
    }

    @GetMapping("ingreds-of/meals")
    Response<List<IngredQuantityDTO>> getIngredsOfMeals(@RequestParam("ids") List<Long> mealIds) {
        return Response.ok(queryService.getIngredsOfMeals(mealIds));
    }
}
