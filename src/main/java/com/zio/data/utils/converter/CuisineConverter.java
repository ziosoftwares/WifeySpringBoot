package com.zio.data.utils.converter;

import com.zio.recipe.data.entity.Cuisine;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CuisineConverter extends EnumSetConverter {
    public CuisineConverter() {
        super(Cuisine.class);
    }
}
