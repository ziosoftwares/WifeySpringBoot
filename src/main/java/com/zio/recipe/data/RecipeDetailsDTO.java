package com.zio.recipe.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDetailsDTO {
    private Long id;
    private List<IngredQuantityDTO> ingredients;  // key=id of ingred, value = quantity required for this recipe
    private List<InstructionDTO> instructions;
}
