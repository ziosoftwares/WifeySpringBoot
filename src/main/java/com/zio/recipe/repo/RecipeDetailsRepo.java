package com.zio.recipe.repo;

import com.zio.recipe.data.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecipeDetailsRepo extends JpaRepository<RecipeDetails, Long> {

    @Query("select r.instructions from RecipeDetails r where r.recipeId = ?1")
    List<Instruction> findInstructionsByRecipeId(Long id);

    @Query("select r.ingredients from RecipeDetails r where r.recipeId = ?1")
    List<IngredQuantity> findIngredientsByRecipeId(Long id);
}
