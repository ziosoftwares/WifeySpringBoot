package com.zio.data.entity.meal;

import java.util.Map;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String instructions;

    @ElementCollection
    @CollectionTable(name = "RecipeIngredQuantity",
            joinColumns = @JoinColumn(name = "recipe_id"))

    @MapKeyJoinColumn(name = "ingredId") // <-- simple column, not a join
    @Column(name = "quantity", nullable = false, scale = 3)
    private Map<Ingred, Double> ingredients;
}
