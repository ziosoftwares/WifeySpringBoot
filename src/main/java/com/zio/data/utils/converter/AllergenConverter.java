package com.zio.data.utils.converter;

import com.zio.data.Allergen;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AllergenConverter extends EnumSetConverter {
    public AllergenConverter() {
        super(Allergen.class);
    }
}
