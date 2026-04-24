package com.zio.recipe.repo;

import com.zio.recipe.data.entity.Recipe;
import com.zio.recipe.data.entity.RecipeDetails;
import com.zio.recipe.data.entity.RecipeMetas;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepo extends JpaRepository<Recipe, Long> {

    List<Recipe> findByNameLike(String name, Pageable pageable);

    @Query("select m from RecipeMetas m where m.recipeId = ?1")
    RecipeMetas findMetasById(Long recipeId);

    @Query("select d from RecipeDetails d where d.recipeId = ?1")
    RecipeDetails findDetailsById(Long recipeId);

    @Query("select r.id from Reception r")
    List<Long> findBestReceived(Pageable pageable);

}
