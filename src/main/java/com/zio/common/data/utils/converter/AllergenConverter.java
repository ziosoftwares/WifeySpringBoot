package com.zio.common.data.utils.converter;

import com.zio.recipe.data.entity.Allergen;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AllergenConverter extends EnumSetConverter {
    public AllergenConverter() {
        super(Allergen.class);
    }
}
