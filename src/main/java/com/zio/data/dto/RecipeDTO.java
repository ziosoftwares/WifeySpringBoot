package com.zio.data.dto;

import com.zio.data.Allergen;
import com.zio.data.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.EnumSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {

    private Long id;
    private String name;
    private RecipeMetasDTO metas;
    private RecipeDetailsDTO details;

}

