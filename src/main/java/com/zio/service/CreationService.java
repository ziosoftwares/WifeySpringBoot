package com.zio.service;

import com.zio.data.api.Error;
import com.zio.data.dto.GeneralDTO;
import com.zio.data.entity.*;
import com.zio.repo.*;
import com.zio.util.SessionManager;
import com.zio.util.ZioException;
import com.zio.util.ZioRunTimeException;
import com.zio.data.dto.IngredDTO;
import com.zio.data.dto.RecipeDTO;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Transactional
@Service
public class CreationService {

    @Value("${content.recipe}")
    private String recipeDir;

    @Autowired
    IngredRepo ingredRepo;
    @Autowired
    MealRepo mealRepo;
    @Autowired
    RecipeRepo recipeRepo;
    @Autowired
    UserRepo userRepo;

    ModelMapper mapper = new ModelMapper();

    final String tag = this.getClass().getName();

    public Long makeIngred(IngredDTO ingredDTO) {
        Ingred ingred = mapper.map(ingredDTO, Ingred.class);
        ingred.setNutrients(mapper.map(ingredDTO.getNutrients(), Nutrients.class));
        ingred.setId(null);
        return ingredRepo.save(ingred).getId();
    }

    public Long makeRecipe(RecipeDTO recipeDTO) throws ZioException {

        Recipe recipe = mapper.map(recipeDTO, Recipe.class);
        recipe.setDetails(mapper.map(recipeDTO.getDetails(), RecipeDetails.class));
        recipe.setMetas(mapper.map(recipeDTO.getMetas(), RecipeMetas.class));
        recipe.setAuthor(userRepo.findById(SessionManager.getUserId()).orElseThrow(() -> new ZioException(new Error(404, "NO_SUCH_USER", 1))));

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
                        new IngredQuantity(ingredRepo.findById(entry.getKey()).orElseThrow(() -> new ZioRunTimeException(new Error(404, "NO_SUCH_INGRED", 4))), entry.getValue(), details))
                .toList();
    }

    public GeneralDTO checkMeal(ArrayList<Long> ids) throws ZioException {
        Optional<Meal> meal = mealRepo.findMealByRecipeIds(
                ids.getFirst(),
                ids.subList(1, ids.size()),
                ids.size() - 1
        );
        Meal result = meal.isPresent() ? meal.get() : makeMeal(ids);
        return result.mapToDTO();
    }

    public Meal makeMeal(ArrayList<Long> ids) throws ZioException {
        Meal meal = new Meal();
        meal.setMain(recipeRepo.findById(ids.getFirst()).orElseThrow(() -> new ZioException(new Error(404, "NO_SUCH_RECIPE", 3))));
        ids.removeFirst();
        meal.setSides(new HashSet<>());
        for (Long s : ids) {
            meal.getSides().add(recipeRepo.findById(s).orElseThrow(() -> new ZioException(new Error(404, "NO_SUCH_RECIPE", 3))));
        }
        meal.setName(generateName(meal));
        meal.setId(null);
        return mealRepo.save(meal);
    }

    private String generateName(Meal meal) {
        StringBuilder nameBuilder = new StringBuilder(meal.getMain().getName());
        if (!meal.getSides().isEmpty())
            nameBuilder.append(" | ");
        meal.getSides().forEach(s -> nameBuilder.append(s.getName()).append(", "));
        nameBuilder.delete(nameBuilder.length() - 2, nameBuilder.length() - 1);
        return nameBuilder.toString();
    }

    /// ///////////// CDN

    public Boolean addRecipePicture(MultipartFile image, Long recipeId) throws ZioException {
        try {
            Path path = Paths.get(recipeDir);
            if (!Files.exists(path))
                Files.createDirectories(path);
            String imageType = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf('.'));
            String filename = recipeId.toString() + imageType;
            Files.copy(image.getInputStream(), path.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            recipeRepo.findById(recipeId).ifPresent(recipe -> {
                recipe.setImgUrl("public/recipeImages/" + filename);
            });
            return true;
        } catch (IOException e) {
            throw new ZioException(new Error(510, "UNABLE_TO_PERSIST", 1));
        }
    }


    /// ////////////// create DB
    public void makeIngreds(List<IngredDTO> ingreds) {
        ingreds.forEach(this::makeIngred);
    }

    public void makeRecipes(List<RecipeDTO> recipes) throws ZioException {
        for (var recipe : recipes) makeRecipe(recipe);
    }

    public void makeMeals(List<ArrayList<Long>> mealList) throws ZioException {
        for (ArrayList<Long> ids : mealList) makeMeal(ids);
    }
}
