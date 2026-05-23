package com.zio.user.data.entity;


import com.zio.recipe.data.entity.Allergen;
import com.zio.recipe.data.entity.Cuisine;
import com.zio.recipe.data.entity.Diet;
import com.zio.common.data.utils.converter.AllergenConverter;
import com.zio.common.data.utils.converter.CuisineConverter;
import com.zio.common.data.utils.converter.DietConverter;
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

    private Diet diet;
    @Convert(converter = CuisineConverter.class)
    private EnumSet<Cuisine> cuisines;
    @Convert(converter = AllergenConverter.class)
    private EnumSet<Allergen> allergens;

    public Preferences(Long userId) {
        this.userId = userId;
    }
}


