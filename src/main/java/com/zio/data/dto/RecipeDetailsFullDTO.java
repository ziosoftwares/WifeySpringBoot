package com.zio.data.dto;

import com.zio.data.entity.IngredQuantity;
import com.zio.data.entity.Instruction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDetailsFullDTO {
    private Long id;
    private List<IngredQuantityDTO> ingredients;  // key=id of ingred, value = quantity required for this recipe
    private List<InstructionDTO> instructions;
}
