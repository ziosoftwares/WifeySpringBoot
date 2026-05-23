package com.zio.recipe.service;

import com.zio.common.data.RecipeInfo;
import com.zio.common.data.api.Error;
import com.zio.common.data.GeneralDTO;
import com.zio.common.data.utils.converter.AllergenConverter;
import com.zio.common.data.utils.converter.CuisineConverter;
import com.zio.common.util.ZioRunTimeException;
import com.zio.ingred.service.IngredQueryService;
import com.zio.recipe.data.*;
import com.zio.recipe.data.entity.Instruction;
import com.zio.recipe.data.entity.RecipeMetas;
import com.zio.recipe.data.util.ObjectMapper;
import com.zio.recipe.data.entity.Recipe;
import com.zio.recipe.repo.*;
import com.zio.user.data.entity.Author;
import com.zio.user.data.entity.Preferences;
import com.zio.user.repo.AuthorRepo;
import com.zio.common.util.SessionManager;
import com.zio.common.util.ZioException;
import com.zio.user.repo.PreferenceRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    PreferenceRepo preferencesRepo;
    @Autowired
    AuthorRepo authorRepo;

    @Autowired
    IngredQueryService ingredService;

    public List<GeneralDTO> getRecipeLike(String name) {
        List<Recipe> result = recipeRepo.findByNameLike(name + "%", PageRequest.of(0, 10));
        if (result.isEmpty()) result = recipeRepo.findByNameLike("%" + name + "%", PageRequest.of(0, 10));

        return result.stream().map(recipe -> ObjectMapper.toGenDTO(recipe, authorRepo.findById(recipe.getAuthorId()).orElse(new Author()).getUserName())).toList();
    }

    public RecipeDTO getRecipe(Long id) throws ZioException {
        var recipe = recipeRepo.findById(id).orElseThrow(() -> new ZioException(new Error(404, 3, "NO_SUCH_RECIPE")));

        var result = ObjectMapper.toRecipeDTO(
                recipe,
                authorRepo.findById(recipe.getAuthorId()),
                metasRepo.findById(recipe.getId()),
                Optional.of(detailsRepo.findIngredientsByRecipeId(recipe.getId())),
                Optional.of(new ReceptionDTO(receptionRepo.findLikesCountByRecipeId(recipe.getId()), receptionRepo.isLikedBy(recipe.getId(), SessionManager.getUserId())))
        );
        result.getIngredQuan().forEach(it -> {
            try {
                it.setIngred(ingredService.getIngred(it.getIngred().getId()));
            } catch (ZioException e) {
                throw new ZioRunTimeException(e);
            }
        });

        return result;
    }

    public GeneralDTO getRecipeDTO(Long id) throws ZioException {
        var recipe = recipeRepo.findById(id).orElseThrow(() -> new ZioException(new Error(404, 3, "NO_SUCH_RECIPE")));
        return ObjectMapper.toGenDTO(recipe, "");
    }

    public List<InstructionDTO> getRecipeInstruction(Long id) throws ZioException {

        List<Instruction> instructions = detailsRepo.findInstructionsByRecipeId(id);
        if (instructions.isEmpty()) throw new ZioException(new Error(404, 3, "NO_SUCH_RECIPE"));
        return instructions.stream().map(ins -> new InstructionDTO(ins.getId(), ins.getDuration(), ins.getInstruction())).toList();
    }

    public List<RecipeInfo> getRecipesByPreference(Long userId) throws ZioException {
        Preferences prefs = preferencesRepo.findById(userId).orElseThrow(() -> new ZioException(new Error(404, 1, "NO_SUCH_USER")));
        var cuisines = new CuisineConverter().toOrdinalsList(prefs.getCuisines());
        var allergens = new AllergenConverter().convertToDatabaseColumn(prefs.getAllergens());

        List<RecipeMetas> metas = metasRepo.findByPreference(prefs.getDiet(), cuisines, allergens);

        return metas.stream().map(meta -> new ModelMapper().map(meta, RecipeInfo.class)).peek(info -> {
            var recipe = recipeRepo.findById(info.getId()).get();
            info.setAuthorId(recipe.getAuthorId());
            info.setName(recipe.getName());
        }).toList();
    }

    public String getRecipeImgUrl(Long id) throws ZioException {
        return recipeRepo.findById(id).orElseThrow(() -> new ZioException(new Error(404, 3, "NO_SUCH_RECIPE"))).getImgUrl();
    }
}
