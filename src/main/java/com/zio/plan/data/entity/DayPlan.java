package com.zio.plan.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DayPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = "breakfast_id")
    private Meal breakfast;

    @ManyToOne
    @JoinColumn(name = "lunch_id")
    private Meal lunch;

    @ManyToOne
    @JoinColumn(name = "dinner_id")
    private Meal dinner;
}
