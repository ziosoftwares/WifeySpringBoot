package com.zio.data.dto;

import com.zio.data.entity.Recipe;
import com.zio.data.entity.RecipeDetails;
import jakarta.persistence.*;
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
    private RecipeFullDTO main;
    private Set<RecipeFullDTO> sides;
}
