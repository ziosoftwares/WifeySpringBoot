package com.zio.recipe.data;


import com.zio.ingred.data.entity.Nutrition;
import com.zio.recipe.data.entity.*;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeMetasDTO {

    private Long id;
    private Integer duration;
    private Difficulty difficulty;

    private MealType mealType;
    private Role role;

    private Diet diet;
    private Cuisine cuisine;

    private Set<Allergen> allergens;
    private Nutrition nutrition;

}
