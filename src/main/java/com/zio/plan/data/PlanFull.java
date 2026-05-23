package com.zio.plan.data;

import com.zio.plan.data.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanFull {
    private Long id;
    private Long authorId;
    private String authorName;
    private String title;
    private Integer saves = 0;
    private List<String> imgUrls = Collections.emptyList();

    private Integer days;
    private List<DayPlanFull> dayPlans;

    public PlanFull(Plan it) {
        id = it.getId();
        authorId = it.getAuthorId();
        authorName = it.getAuthorName();
        title = it.getTitle();
        saves = it.getSaves();
        days = it.getDayPlans().size();
        imgUrls = it.getImgUrls();
        dayPlans = new ArrayList<>();
    }
}
