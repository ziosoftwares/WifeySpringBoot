package com.zio.plan.data;

import com.zio.common.data.RecipeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanRequest {
    private Integer days;
    private List<RecipeInfo> recipes;
}
