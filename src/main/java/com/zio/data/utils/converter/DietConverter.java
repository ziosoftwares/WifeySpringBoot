package com.zio.data.utils.converter;

import com.zio.recipe.data.entity.Diet;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DietConverter extends EnumSetConverter {
    public DietConverter() {
        super(Diet.class);
    }
}
