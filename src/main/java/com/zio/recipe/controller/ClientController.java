package com.zio.recipe.controller;

import com.zio.common.util.ZioException;
import com.zio.recipe.data.RecipeCard;
import com.zio.recipe.data.RecipeDTO;
import com.zio.recipe.service.ClientService;
import com.zio.recipe.service.RecipeQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("client")
public class ClientController {

    @Autowired
    private RecipeQueryService queryService;
    @Autowired
    private ClientService clientService;

    @PostMapping("featured/recipes")
    List<RecipeCard> getFeaturedRecipes(@RequestBody Map<String, Object> filter) {
        return clientService.getFeatured(filter);
    }

    @PutMapping("like/{id}")
    @ResponseStatus(HttpStatus.OK)
    void likeRecipe(@PathVariable Long id) throws ZioException {
        clientService.likeRecipe(id);
    }

}
