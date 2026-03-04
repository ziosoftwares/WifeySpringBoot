package com.zio.service;

import com.zio.data.entity.*;
import com.zio.util.ZioRunTimeException;
import com.zio.data.dto.IngredDTO;
import com.zio.data.dto.MealDTO;
import com.zio.data.dto.RecipeDTO;
import com.zio.repo.IngredRepo;
import com.zio.repo.MealRepo;
import com.zio.repo.RecipeRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class CreationService {

    @Autowired
    IngredRepo ingredRepo;
    @Autowired
    MealRepo mealRepo;
    @Autowired
    RecipeRepo recipeRepo;

    ModelMapper mapper = new ModelMapper();

    final String tag = this.getClass().getName();

    public Long makeIngred(IngredDTO ingredDTO) {
        Ingred ingred = mapper.map(ingredDTO, Ingred.class);
        ingred.setNutrients(mapper.map(ingredDTO.getNutrients(), Nutrients.class));

        ingred.setId(null);
        return ingredRepo.save(ingred).getId();
    }

    public Long makeRecipe(RecipeDTO recipeDTO) {

        Recipe recipe = mapper.map(recipeDTO, Recipe.class);
        recipe.setDetails(mapper.map(recipeDTO.getDetails(), RecipeDetails.class));
        recipe.setMetas(mapper.map(recipeDTO.getMetas(), RecipeMetas.class));
        recipe.getDetails().setIngredients(getIngredList(recipeDTO.getDetails().getIngredients(), recipe.getDetails()));
        recipe.getDetails().getInstructions().forEach(i -> i.setDetails(recipe.getDetails()));

        recipe.getDetails().setId(null);
        recipe.getDetails().setRecipe(recipe);
        recipe.getMetas().setId(null);
        recipe.getMetas().setRecipe(recipe);
        recipe.setId(null);

        return recipeRepo.save(recipe).getId();
    }

    private List<IngredQuantity> getIngredList(Map<Long, Double> ingredients, RecipeDetails details) {

        return ingredients.entrySet().stream()
                .map(entry ->
                        new IngredQuantity(ingredRepo.findById(entry.getKey()).orElseThrow(() -> new ZioRunTimeException("NO_SUCH_INGRED")), entry.getValue(), details))
                .toList();
    }

    public Long makeMeal(MealDTO mealDTO) throws ZioRunTimeException {
        Meal meal = mapper.map(mealDTO, Meal.class);
        meal.setMain(recipeRepo.findById(mealDTO.getMain()).orElseThrow(() -> new ZioRunTimeException(tag + ".NO_SUCH_RECIPE")));

        meal.setSides(new HashSet<>());
        mealDTO.getSides().forEach(s -> {
            meal.getSides().add(recipeRepo.findById(s).orElseThrow(() -> new ZioRunTimeException(tag + ".NO_SUCH_RECIPE")))
            ;
        });

        meal.setName(generateName(meal));
        meal.setId(null);

        return mealRepo.save(meal).getId();
    }

    private String generateName(Meal meal) {
        StringBuilder nameBuilder = new StringBuilder(meal.getMain().getName());
        if (!meal.getSides().isEmpty())
            nameBuilder.append(" with ");
        meal.getSides().forEach(s -> nameBuilder.append(s.getName()).append(", "));
        nameBuilder.delete(nameBuilder.length() - 2, nameBuilder.length() - 1);
        return nameBuilder.toString();
    }

    public void makeIngreds(List<IngredDTO> ingreds) {
        ingreds.forEach(this::makeIngred);
    }

    public void makeRecipes(List<RecipeDTO> recipe) {
        recipe.forEach(this::makeRecipe);
    }

    public void makeMeals(List<MealDTO> meals) {
        meals.forEach(this::makeMeal);
    }
}
