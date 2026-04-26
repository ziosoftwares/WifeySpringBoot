package com.zio.plan.data.util;

import com.zio.common.data.dto.GeneralDTO;
import com.zio.plan.data.entity.Meal;

public class ObjectMapper {
    public static GeneralDTO toGenDTO(Meal meal) {
        return new GeneralDTO(meal.getId(), meal.getName());
    }
}
