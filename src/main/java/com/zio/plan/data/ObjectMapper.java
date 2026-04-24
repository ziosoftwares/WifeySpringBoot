package com.zio.plan.data;

import com.zio.data.dto.GeneralDTO;
import com.zio.recipe.data.entity.Meal;

public class ObjectMapper {
    public static GeneralDTO toGenDTO(Meal meal) {
        return new GeneralDTO(meal.getId(), meal.getName(), meal.getMain().getImgUrl());
    }
}
