package com.zio.plan.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DayPlanId implements Serializable {
    private Integer day;
    private Long planId;
    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof DayPlanId that)) return false;

        return planId.equals(that.planId) && day.equals(that.day);
    }

    @Override
    public int hashCode() {
        int result = planId.hashCode();
        result = 31 * result + day.hashCode();
        return result;
    }
}
