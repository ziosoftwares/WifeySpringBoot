package com.zio.data.dto;


import com.zio.data.Allergen;
import com.zio.data.Cuisine;
import com.zio.data.Diet;
import com.zio.data.utils.converter.AllergenConverter;
import com.zio.data.utils.converter.CuisineConverter;
import com.zio.data.utils.converter.DietConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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


