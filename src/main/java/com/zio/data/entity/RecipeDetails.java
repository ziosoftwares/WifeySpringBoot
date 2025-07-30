package com.zio.data.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RecipeDetails {

    @Id
    private Long id;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String instructions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "details", orphanRemoval = true)
    private List<IngredQuantity> ingredients;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    private Recipe recipe;
}
