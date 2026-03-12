package com.zio.service;

import com.zio.data.api.Error;
import com.zio.data.dto.*;
import com.zio.data.entity.Ingred;
import com.zio.data.entity.Meal;
import com.zio.data.entity.Nutrients;
import com.zio.data.entity.Recipe;
import com.zio.repo.IngredRepo;
import com.zio.repo.MealRepo;
import com.zio.repo.RecipeRepo;
import com.zio.util.ZioException;
import com.zio.util.ZioRunTimeException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueryService {

    @Autowired
    RecipeRepo recipeRepo;

    @Autowired
    IngredRepo ingredRepo;

    @Autowired
    MealRepo mealRepo;

    private final ModelMapper mapper = new ModelMapper();

    public List<GeneralDTO> getIngredsLike(String name) {
        List<Ingred> result = ingredRepo.findByNameLike(name + "%", PageRequest.of(0, 10));
        if (result.isEmpty()) result = ingredRepo.findByNameLike("%" + name + "%", PageRequest.of(0, 10));

        return result.stream().map(Ingred::makeGenDTO).toList();
    }

    public List<GeneralDTO> getRecipeLike(String name) {
        List<Recipe> result = recipeRepo.findByNameLike(name + "%", PageRequest.of(0, 10));
        if (result.isEmpty()) result = recipeRepo.findByNameLike("%" + name + "%", PageRequest.of(0, 10));

        return result.stream().map(Recipe::makeGeneralDTO).toList();
    }

    public List<GeneralDTO> getMealsLike(String name) {
        List<Meal> result = mealRepo.findByNameLike(name + "%", PageRequest.of(0, 10));
        if (result.isEmpty()) result = mealRepo.findByNameLike("%" + name + "%", PageRequest.of(0, 10));
        return result.stream().map(meal -> new GeneralDTO(meal.getId(), meal.getName(), null, meal.getMain().getImgUrl(), null)).toList();
    }

    public MealFullDTO getMealById(Long id) throws ZioException {
        Meal meal = mealRepo.findById(id).orElseThrow(() -> new ZioException(new Error(404, "NO_SUCH_MEAL_ID", 2)));

        MealFullDTO dto = new MealFullDTO();
        dto.setId(meal.getId());
        dto.setName(meal.getName());
        dto.setMain(RecipeFullDTO.create(meal.getMain()));
        dto.setSides(meal.getSides().stream().map(RecipeFullDTO::create).collect(Collectors.toSet()));

        return dto;
    }

    public List<IngredQuantityDTO> getIngredsOfMeals(List<Long> mealIds) {
        return mealRepo.getIngreds(mealIds).stream().map(dto ->
                new IngredQuantityDTO(ingredRepo.findById(dto.getIngredId()).orElseThrow(() -> new ZioRunTimeException(new Error(404, "NO_SUCH_INGRED", 4))), dto.getQuantity())).toList();
    }

    public RecipeFullDTO getRecipe(Long id) throws ZioException {
        return RecipeFullDTO.create(recipeRepo.findById(id).orElseThrow(() -> new ZioException(new Error(404, "NO_SUCH_RECIPE", 3))));
    }

    public IngredDTO getIngred(Long id) throws ZioException {
        Ingred ingred = ingredRepo.findById(id).orElseThrow(() -> new ZioException(new Error(404, "NO_SUCH_INGRED", 4)));
        IngredDTO dto = mapper.map(ingred, IngredDTO.class);
        dto.setNutrients(new Nutrients(ingred.getNutrients()));

        return dto;
    }

    public List<IngredDTO> getIngreds(List<Long> ids) {
        return ingredRepo.findAllById(ids).stream().map(i -> {
            IngredDTO dto = mapper.map(i, IngredDTO.class);
            dto.setNutrients(new Nutrients(i.getNutrients()));
            return dto;
        }).toList();
    }
}
