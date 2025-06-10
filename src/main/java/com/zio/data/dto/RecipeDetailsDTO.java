package com.zio.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDetailsDTO {
    private Long id;
    private Map<Long, Double> ingredients;  // key=id of ingred, value = quantity required for this recipe
    private String instructions;
}
