package com.zio.apis;

import com.zio.data.api.Response;
import com.zio.data.dto.GeneralDTO;
import com.zio.data.dto.RecipeDTO;
import com.zio.service.ClientService;
import com.zio.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("client")
public class ClientController {

    @Autowired
    private QueryService queryService;
    @Autowired
    private ClientService clientService;

    @GetMapping("featured/recipe")
    Response<RecipeDTO> getFeaturedRecipe() {
        return Response.ok(clientService.getFeaturedRecipe());
    }

    @GetMapping("featured/recipes")
    ResponseEntity<List<GeneralDTO>> getFeaturedRecipes() {
        return ResponseEntity.ok(clientService.getFeatured());
    }


}
