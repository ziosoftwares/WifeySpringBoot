package com.zio.service;

import com.zio.data.dto.IdNameDTO;
import com.zio.data.entity.Recipe;
import com.zio.repo.IngredRepo;
import com.zio.repo.MealRepo;
import com.zio.repo.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    RecipeRepo recipeRepo;

    @Autowired
    IngredRepo ingredRepo;

    @Autowired
    MealRepo mealRepo;

    public List<IdNameDTO> getFeatured() {

        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        List<Recipe> recipes = recipeRepo.findAll(pageable).getContent();

        return recipes.stream().map(r -> new IdNameDTO(r.getId(), r.getName())).toList();
    }
}
