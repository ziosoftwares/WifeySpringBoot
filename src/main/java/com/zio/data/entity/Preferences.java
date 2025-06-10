package com.zio.data.entity;


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
@Entity
public class Preferences {

    @Id
    private Long userId;
    //    Integer maxTime;
//    Difficulty difficulty;

    @Convert(converter = DietConverter.class)
    private EnumSet<Diet> diets;
    @Convert(converter = CuisineConverter.class)
    private EnumSet<Cuisine> cuisines;
    @Convert(converter = AllergenConverter.class)
    private EnumSet<Allergen> allergens;


}


