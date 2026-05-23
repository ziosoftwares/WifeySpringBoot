package com.zio.plan.data;

import com.zio.plan.data.entity.DayPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayPlanDTO {
    private Integer day;
    private List<Long> breakfast, lunch, dinner;  //recipe ids

    public DayPlanDTO(DayPlan dayPlan) {
        day = dayPlan.getDay();
        breakfast = dayPlan.getBreakfast();
        lunch = dayPlan.getLunch();
        dinner = dayPlan.getDinner();
    }
}
