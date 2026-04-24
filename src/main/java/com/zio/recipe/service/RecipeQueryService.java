package com.zio.recipe.service;

import com.zio.data.api.Error;
import com.zio.data.dto.*;
import com.zio.ingred.data.IngredDTO;
import com.zio.recipe.data.*;
import com.zio.recipe.data.entity.Instruction;
import com.zio.recipe.data.entity.Meal;
import com.zio.recipe.data.util.ObjectMapper;
import com.zio.recipe.data.entity.Recipe;
import com.zio.recipe.repo.*;
import com.zio.user.data.entity.Author;
import com.zio.user.repo.AuthorRepo;
import com.zio.util.SessionManager;
import com.zio.util.ZioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeQueryService {

    @Autowired
    RecipeRepo recipeRepo;
    @Autowired
    RecipeMetasRepo metasRepo;
    @Autowired
    RecipeDetailsRepo detailsRepo;
    @Autowired
    ReceptionRepo receptionRepo;

    @Autowired
    MealRepo mealRepo;

    @Autowired
    AuthorRepo authorRepo;

    public List<GeneralDTO> getRecipeLike(String name) {
        List<Recipe> result = recipeRepo.findByNameLike(name + "%", PageRequest.of(0, 10));
        if (result.isEmpty()) result = recipeRepo.findByNameLike("%" + name + "%", PageRequest.of(0, 10));

        return result.stream().map(recipe -> ObjectMapper.toGenDTO(recipe, authorRepo.findById(recipe.getAuthorId()).orElse(new Author()).getUserName())).toList();
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
        dto.setMain(ObjectMapper.toRecipeDTO(meal.getMain()));
        dto.setSides(meal.getSides().stream().map(ObjectMapper::toRecipeDTO).collect(Collectors.toSet()));

        return dto;
    }

    public RecipeDTO getRecipe(Long id) throws ZioException {
        var recipe = recipeRepo.findById(id).orElseThrow(() -> new ZioException(new Error(404, "NO_SUCH_RECIPE", 3)));

        return ObjectMapper.toRecipeDTO(
                recipe,
                authorRepo.findById(recipe.getAuthorId()),
                metasRepo.findById(recipe.getId()),
                Optional.of(detailsRepo.findIngredientsByRecipeId(recipe.getId())),
                Optional.of(new ReceptionDTO(receptionRepo.findLikesCountByRecipeId(recipe.getId()), receptionRepo.isLikedBy(recipe.getId(), SessionManager.getUserId())))
        );
    }

    public List<InstructionDTO> getRecipeInstruction(Long id) throws ZioException {

        List<Instruction> instructions = detailsRepo.findInstructionsByRecipeId(id);
        if (instructions.isEmpty()) throw new ZioException(new Error(404, "NO_SUCH_RECIPE", 3));
        return instructions.stream().map(ins -> new InstructionDTO(ins.getId(), ins.getDuration(), ins.getInstruction())).toList();
    }
}
