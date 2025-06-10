package com.zio.data.dto;


import com.zio.data.Allergen;
import com.zio.data.Cuisine;
import com.zio.data.Diet;
import com.zio.data.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.EnumSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeMetasDTO {

    private Long id;
    private Integer maxTime;
    private Difficulty difficulty;

    private Diet diet;
    private Cuisine cuisine;

    private EnumSet<Allergen> allergens;

}
