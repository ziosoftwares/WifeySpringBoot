package com.zio.recipe.data.entity;

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
    private Long recipeId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipeId", orphanRemoval = true)
    private List<Instruction> instructions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipeId", orphanRemoval = true)
    private List<IngredQuantity> ingredients;

}
