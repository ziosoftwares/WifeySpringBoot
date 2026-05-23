package com.zio.common.data;

import com.zio.ingred.data.entity.Nutrition;
import com.zio.recipe.data.entity.*;
import lombok.Data;

@Data
public class RecipeInfo {
    private Long id;
    private String name;
    private Long authorId;
    private Integer duration;
    private Difficulty difficulty;
    private MealType mealType;
    private Role role;
    private Cuisine cuisine;
    private Nutrition nutrition;
}
