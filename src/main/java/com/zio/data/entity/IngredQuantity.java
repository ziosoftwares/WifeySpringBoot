package com.zio.data.entity;

import com.zio.data.utils.IngredQuantityId;
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
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Ingred ingred;
    private Double quantity;

    @Id
    @ManyToOne
    @JoinColumn(name = "recipeId", nullable = false)
    private RecipeDetails details;

}
