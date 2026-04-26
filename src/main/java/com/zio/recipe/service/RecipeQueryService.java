package com.zio.recipe.service;

import com.zio.common.data.api.Error;
import com.zio.common.data.dto.GeneralDTO;
import com.zio.recipe.data.*;
import com.zio.recipe.data.entity.Instruction;
import com.zio.recipe.data.util.ObjectMapper;
import com.zio.recipe.data.entity.Recipe;
import com.zio.recipe.repo.*;
import com.zio.user.data.entity.Author;
import com.zio.user.repo.AuthorRepo;
import com.zio.common.util.SessionManager;
import com.zio.common.util.ZioException;
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
    AuthorRepo authorRepo;

    public List<GeneralDTO> getRecipeLike(String name) {
        List<Recipe> result = recipeRepo.findByNameLike(name + "%", PageRequest.of(0, 10));
        if (result.isEmpty()) result = recipeRepo.findByNameLike("%" + name + "%", PageRequest.of(0, 10));

        return result.stream().map(recipe -> ObjectMapper.toGenDTO(recipe, authorRepo.findById(recipe.getAuthorId()).orElse(new Author()).getUserName())).toList();
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
