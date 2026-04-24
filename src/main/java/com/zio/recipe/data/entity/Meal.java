package com.zio.recipe.data.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Recipe main;

    @ManyToMany()
    @JoinTable(name = "mealSides",
            joinColumns = @JoinColumn(name = "mealId"),
            inverseJoinColumns = @JoinColumn(name = "sideId"))
    private Set<Recipe> sides;

}
