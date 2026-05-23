package com.zio.plan.data;

import com.zio.plan.data.entity.DayPlan;
import com.zio.plan.data.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanDTO {
    private Long id;
    private Long authorId;
    private String authorName;
    private String title;
    private Integer saves = 0;
    private List<String> imgUrls = Collections.emptyList();

    private Integer days;
    private List<DayPlanDTO> dayPlans;

    public PlanDTO(Plan it) {
        id = it.getId();
        authorId = it.getAuthorId();
        authorName = it.getAuthorName();
        title = it.getTitle();
        saves = it.getSaves();
        days = it.getDayPlans().size();
        imgUrls = it.getImgUrls();
        dayPlans = it.getDayPlans().stream().map(DayPlanDTO::new).toList();
    }
}
