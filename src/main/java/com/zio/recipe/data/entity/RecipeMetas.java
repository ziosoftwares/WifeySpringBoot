package com.zio.recipe.data.entity;


import java.util.EnumSet;
import java.util.Set;

import com.zio.data.utils.converter.AllergenConverter;
import com.zio.ingred.data.entity.Nutrition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RecipeMetas {

    @Id
    private Long recipeId;

    private Integer duration;
    @Enumerated
    private Difficulty difficulty;
    @Enumerated
    private Cuisine cuisine;
    @Enumerated
    private Diet diet;
    @Convert(converter = AllergenConverter.class)
    private EnumSet<Allergen> allergens;

    @Embedded
    private Nutrition nutrition;
}
