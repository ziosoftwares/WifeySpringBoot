package com.zio.data.dto;

import com.zio.data.entity.meal.Meal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DayPlan {
    Meal breakfast, lunch, dinner;
}
