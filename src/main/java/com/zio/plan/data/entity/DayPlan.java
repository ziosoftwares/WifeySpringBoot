package com.zio.plan.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(DayPlanId.class)
public class DayPlan {

    @Id
    @ManyToOne
    @JoinColumn(name = "planId")
    private Plan planId;

    @Id
    private Integer day;

    @ElementCollection
    private List<Long> breakfast; //recipe ids

    @ElementCollection
    private List<Long> lunch;

    @ElementCollection
    private List<Long> dinner;

}
