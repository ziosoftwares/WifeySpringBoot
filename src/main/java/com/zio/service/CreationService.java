package com.zio.service;

import com.zio.util.ZioException;
import com.zio.util.ZioRunTimeException;
import com.zio.data.dto.IngredDTO;
import com.zio.data.dto.MealDTO;
import com.zio.data.dto.RecipeDTO;
import com.zio.data.entity.meal.*;
import com.zio.repo.IngredRepo;
import com.zio.repo.MealRepo;
import com.zio.repo.RecipeRepo;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

        ingred.getNutrients().setId(null);
        ingred.setId(null);

        return ingredRepo.save(ingred).getId();
    }

    public Long makeRecipe(RecipeDTO recipeDTO) {

        Recipe recipe = mapper.map(recipeDTO, Recipe.class);
        recipe.setDetails(mapper.map(recipeDTO.getDetails(), RecipeDetails.class));
        recipe.setMetas(mapper.map(recipeDTO.getMetaData(), RecipeMetas.class));
        recipe.getDetails().setIngredients(getIngredList(recipeDTO.getDetails().getIngredients()));

        recipe.getDetails().setId(null);
        recipe.getMetas().setId(null);
        recipe.setId(null);

        return recipeRepo.save(recipe).getId();
    }

    private Map<Ingred, Double> getIngredList(Map<Long, Double> ingredients) {

        return ingredients.entrySet().stream().collect(Collectors.toMap(
                entry -> ingredRepo.findById(entry.getKey()).orElseThrow(() -> new ZioRunTimeException(tag + ".NO_SUCH_INGRED")),
                Map.Entry::getValue
        ));
    }

    public Long makeMeal(MealDTO mealDTO) throws ZioRunTimeException {
        Meal meal = mapper.map(mealDTO, Meal.class);
        meal.setMain(recipeRepo.findById(mealDTO.getMain()).orElseThrow(() -> new ZioRunTimeException(tag + ".NO_SUCH_RECIPE")));
        meal.setSides(mealDTO.getSides().stream()
                .map(s -> recipeRepo.findById(s).orElseThrow(() -> new ZioRunTimeException(tag + ".NO_SUCH_RECIPE")))
                .collect(Collectors.toSet()));
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
