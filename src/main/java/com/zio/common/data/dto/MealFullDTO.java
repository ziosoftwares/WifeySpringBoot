package com.zio.common.data.dto;

import com.zio.recipe.data.RecipeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealFullDTO {

    private Long id;
    private String name;
    private RecipeDTO main;
    private Set<RecipeDTO> sides;
}
