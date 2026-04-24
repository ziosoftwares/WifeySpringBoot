package com.zio.user.data;


import com.zio.recipe.data.entity.Allergen;
import com.zio.recipe.data.entity.Cuisine;
import com.zio.recipe.data.entity.Diet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.EnumSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreferencesDTO {

    private Long userId;
    //    Integer maxTime;
//    Difficulty difficulty;

    private EnumSet<Diet> diets;
    private EnumSet<Cuisine> cuisines;
    private EnumSet<Allergen> allergens;


}


