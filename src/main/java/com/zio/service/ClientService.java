package com.zio.service;

import com.zio.data.dto.RecipeDTO;
import com.zio.data.entity.Recipe;
import com.zio.repo.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    RecipeRepo recipeRepo;

    public List<RecipeDTO> getFeatured() {

        Sort sort = Sort.by("name").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        List<Recipe> recipes = recipeRepo.findAll(pageable).getContent();
        return recipes.stream().map(RecipeDTO::create).toList();
    }

    public RecipeDTO getFeaturedRecipe() {
        return recipeRepo.findAll(PageRequest.of(0, 1))
                .map(RecipeDTO::create).stream().findFirst().get();
    }
}
