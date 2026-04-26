package com.zio.recipe.data.util;

import com.zio.common.data.dto.GeneralDTO;
import com.zio.ingred.data.IngredDTO;
import com.zio.ingred.data.entity.Ingred;
import com.zio.recipe.data.*;
import com.zio.recipe.data.IngredQuantityDTO;
import com.zio.recipe.data.entity.*;
import com.zio.user.data.entity.Author;

import java.util.List;
import java.util.Optional;

public class ObjectMapper {

    public static GeneralDTO toGenDTO(Recipe recipe, String authorName) {
        return new GeneralDTO(recipe.getId(), recipe.getName(), recipe.getImgUrl(), authorName);
    }

    public static IngredDTO toDTO(Ingred ingred) {
        return new IngredDTO(ingred.getId(), ingred.getName(), ingred.getImgUrl(), ingred.getCategory(), ingred.getUnit());
    }

    public static RecipeDTO toRecipeDTO(Recipe recipe) {
        return toRecipeDTO(recipe, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static RecipeDTO toRecipeDTO(Recipe recipe, Optional<Author> author, Optional<ReceptionDTO> reception) {
        return toRecipeDTO(recipe, author, Optional.empty(), Optional.empty(), reception);
    }

    public static RecipeDTO toRecipeDTO(Recipe recipe, Optional<Author> author, Optional<RecipeMetas> metas, Optional<List<IngredQuantity>> ingredQuantity, Optional<ReceptionDTO> reception) {

        RecipeDTO dto = new RecipeDTO();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setImgUrl(recipe.getImgUrl());

        author.ifPresent(auth -> {
            dto.setAuthorId(auth.getUserId());
            dto.setAuthorName(auth.getUserName());
        });

        metas.ifPresent(recipeMetas ->
                dto.setMetas(new RecipeMetasDTO(
                        recipe.getId(),
                        recipeMetas.getDuration(),
                        recipeMetas.getDifficulty(),
                        recipeMetas.getDiet(),
                        recipeMetas.getCuisine(),
                        recipeMetas.getAllergens(),
                        recipeMetas.getNutrition())
                ));

        ingredQuantity.ifPresent(iq -> {
            dto.setIngredQuan(iq.stream().map(i -> new IngredQuantityDTO(new IngredDTO(i.getIngredId()), i.getQuantity())).toList());
        });

        reception.ifPresent(recep -> {
            dto.setLikesCount(recep.getLikesCount());
            dto.setLiked(recep.isLiked());
        });

        return dto;
    }

}
