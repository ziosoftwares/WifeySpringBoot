package com.zio.recipe.data.entity;

import com.zio.recipe.data.util.IngredQuantityId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(IngredQuantityId.class)
public class IngredQuantity {

    @Id
    private Long ingredId;
    private Double quantity;

    @Id
    @ManyToOne
    @JoinColumn(name = "recipeId", nullable = false)
    private RecipeDetails recipeId;

}
