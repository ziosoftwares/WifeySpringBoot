package com.zio.plan.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Data
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long authorId;
    @Column(nullable = false)
    private String authorName;

    @Column(nullable = false)
    private String title;
    private Integer saves = 0;

    @ElementCollection
    private List<String> imgUrls = Collections.emptyList();

    @OneToMany(mappedBy = "planId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DayPlan> dayPlans = new ArrayList<>();
}
