package com.zio.recipe.data.util;

import com.zio.common.data.GeneralDTO;
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
        return new IngredDTO(ingred.getId(), ingred.getName(), ingred.getImgUrl(), ingred.getCategory(), ingred.getUnit(), ingred.getAllergen());
    }

    public static IngredDTO toDTOWithNutrition(Ingred ingred) {
        return new IngredDTO(ingred.getId(), ingred.getName(), ingred.getImgUrl(), ingred.getCategory(), ingred.getUnit(), ingred.getAllergen(), ingred.getNutrition());
    }

    public static RecipeDTO toRecipeDTO(Recipe recipe) {
        return toRecipeDTO(recipe, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static RecipeCard toRecipeCard(Recipe recipe, Optional<Author> author, Optional<RecipeMetas> metas, Optional<ReceptionDTO> reception) {
        RecipeCard card = new RecipeCard();
        card.setId(recipe.getId());
        card.setName(recipe.getName());
        card.setImgUrl(recipe.getImgUrl());

        author.ifPresent(auth -> {
            card.setAuthorId(auth.getUserId());
            card.setAuthorName(auth.getUserName());
        });

        metas.ifPresent(recipeMetas -> {
            card.setDiet(recipeMetas.getDiet());
            card.setDuration(recipeMetas.getDuration());
        });

        reception.ifPresent(recep -> {
            card.setLikesCount(recep.getLikesCount());
            card.setLiked(recep.isLiked());
        });
        return card;
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
                        recipeMetas.getMealType(),
                        recipeMetas.getRole(),
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
