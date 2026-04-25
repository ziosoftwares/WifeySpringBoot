package com.zio.recipe.service;

import com.zio.data.api.Error;
import com.zio.data.dto.GeneralDTO;
import com.zio.ingred.data.Category;
import com.zio.ingred.data.entity.Ingred;
import com.zio.ingred.data.entity.Nutrition;
import com.zio.ingred.repo.IngredRepo;
import com.zio.recipe.data.IngredQuantityDTO;
import com.zio.recipe.data.entity.*;
import com.zio.recipe.data.util.ObjectMapper;
import com.zio.recipe.repo.*;
import com.zio.user.repo.AuthorRepo;
import com.zio.user.repo.UserRepo;
import com.zio.util.SessionManager;
import com.zio.util.ZioException;
import com.zio.util.ZioRunTimeException;
import com.zio.recipe.data.RecipeDTO;
import jakarta.transaction.Transactional;
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
    AuthorRepo authorRepo;
    @Autowired
    MealRepo mealRepo;
    @Autowired
    RecipeRepo recipeRepo;
    @Autowired
    RecipeMetasRepo metasRepo;
    @Autowired
    ReceptionRepo receptionRepo;
    @Autowired
    RecipeDetailsRepo detailsRepo;

    @Autowired
    UserRepo userRepo;

    ModelMapper mapper = new ModelMapper();

    final String tag = this.getClass().getName();

    public Long makeIngred(Ingred ingred) {
        ingred.setId(null);
        return ingredRepo.save(ingred).getId();
    }

    public Long makeRecipe(RecipeDTO recipeDTO) throws ZioException {

        Recipe recipe = mapper.map(recipeDTO, Recipe.class);
        recipe.setId(null);
        recipe.setAuthorId(SessionManager.getUserId());
        recipeDTO.setId(recipeRepo.save(recipe).getId());

        RecipeDetails details = populateDetails(recipeDTO);
        RecipeMetas metas = populateMetas(recipeDTO);

        metasRepo.save(metas);
        detailsRepo.save(details);
        receptionRepo.save(new Reception(recipeDTO.getId()));
        authorRepo.increaseRecipe(SessionManager.getUserId());

        return recipeDTO.getId();
    }

    private RecipeDetails populateDetails(RecipeDTO recipeDTO) {
        RecipeDetails details = mapper.map(recipeDTO.getDetails(), RecipeDetails.class);
        details.setRecipeId(recipeDTO.getId());
        details.setIngredients(getIngredList(recipeDTO.getDetails().getIngredients(), details));
        details.getInstructions().forEach(i -> i.setRecipeId(details));
        return details;

    }

    private RecipeMetas populateMetas(RecipeDTO recipeDTO) {
        RecipeMetas metas = mapper.map(recipeDTO.getMetas(), RecipeMetas.class);
        metas.setRecipeId(recipeDTO.getId());
        recipeDTO.getDetails().getIngredients().forEach(dto -> dto.setIngred(ObjectMapper.toDTO(ingredRepo.findById(dto.getIngred().getId()).get())));

        var ingreds = recipeDTO.getDetails().getIngredients();
        metas.setDiet(findDiet(ingreds.stream().map(dto -> dto.getIngred().getCategory()).toList()));
        metas.setAllergens(EnumSet.of(Allergen.NONE));
        ingreds.forEach(dto -> metas.getAllergens().add(dto.getIngred().getAllergen()));

        Nutrition nutrition = ingreds.stream().map(IngredQuantityDTO::calculateNutrition).reduce(new Nutrition(), Nutrition::add);
        metas.setNutrition(nutrition);

        return metas;
    }

    private Diet findDiet(List<Category> categories) {
        if (categories.stream().anyMatch(cat -> cat.equals(Category.FISH)))
            return Diet.PESCATARIAN;
        else if (categories.stream().anyMatch(cat -> cat.equals(Category.MEAT)))
            return Diet.NONVEGETARIAN;
        else if (categories.stream().anyMatch(cat -> cat.equals(Category.EGG)))
            return Diet.EGGETARIAN;
        else if (categories.stream().anyMatch(cat -> cat.equals(Category.DAIRY)))
            return Diet.VEGETARIAN;
        else return Diet.VEGAN;
    }

    private List<IngredQuantity> getIngredList(List<IngredQuantityDTO> ingredients, RecipeDetails details) {

        return ingredients.stream().map(iq ->
                        new IngredQuantity(ingredRepo.findById(iq.getIngred().getId()).orElseThrow(() -> new ZioRunTimeException(new Error(404, "NO_SUCH_INGRED", 4))).getId(), iq.getQuantity(), details))
                .toList();
    }

    public GeneralDTO checkMeal(ArrayList<Long> ids) throws ZioException {
        Optional<Meal> meal = mealRepo.findMealByRecipeIds(
                ids.getFirst(),
                ids.subList(1, ids.size()),
                ids.size() - 1
        );
        Meal result = meal.isPresent() ? meal.get() : makeMeal(ids);
        return ObjectMapper.toGenDTO(result);
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

    public void updateRecipeImg(String mimeType, Long recipeId) throws ZioException {
        String extension = "." + mimeType.substring(mimeType.lastIndexOf('/') + 1);
        recipeRepo.findById(recipeId).orElseThrow(() -> new ZioException(new Error(404, "NO_SUCH_RECIPE", 3)))
                .setImgUrl("recipeImages/recipe" + recipeId + extension);

    }
}
