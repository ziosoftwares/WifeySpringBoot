package com.zio.data.dto;

import com.zio.data.Allergen;
import com.zio.data.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.EnumSet;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeFullDTO {

    private Long id;
    private String name;
    private RecipeMetasDTO metas;
    private RecipeDetailsFullDTO details;

    public static RecipeFullDTO create(Recipe recipe) {
        RecipeFullDTO dto = new RecipeFullDTO();
        dto.id = recipe.getId();
        dto.name = recipe.getName();

        dto.metas = new RecipeMetasDTO(recipe.getId(),
                recipe.getMetas().getDuration(),
                recipe.getMetas().getDifficulty(),
                recipe.getMetas().getDiet(),
                recipe.getMetas().getCuisine(),
                recipe.getMetas().getAllergens());

        List<IngredQuantityDTO> ingredList = recipe.getDetails().getIngredients().stream().map(i -> new IngredQuantityDTO(i.getIngred(), i.getQuantity())).toList();
        dto.details = new RecipeDetailsFullDTO(recipe.getDetails().getId(), ingredList
                , recipe.getDetails().getInstructions().stream().map(i -> new InstructionDTO(i.getId(), i.getDuration(), i.getInstruction())).toList());

        return dto;
    }

}

