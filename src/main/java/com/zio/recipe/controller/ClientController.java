package com.zio.recipe.controller;

import com.zio.data.api.Response;
import com.zio.recipe.data.RecipeDTO;
import com.zio.recipe.service.ClientService;
import com.zio.recipe.service.RecipeQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("client")
public class ClientController {

    @Autowired
    private RecipeQueryService queryService;
    @Autowired
    private ClientService clientService;

    @GetMapping("featured/recipes")
    List<RecipeDTO> getFeaturedRecipes() {
        return clientService.getFeatured();
    }


}
