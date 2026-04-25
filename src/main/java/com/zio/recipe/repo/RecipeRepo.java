package com.zio.recipe.repo;

import com.zio.recipe.data.entity.Recipe;
import com.zio.recipe.data.entity.RecipeDetails;
import com.zio.recipe.data.entity.RecipeMetas;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepo extends JpaRepository<Recipe, Long>{

    List<Recipe> findByNameLike(String name, Pageable pageable);

    @Query("select r.id from Reception r where r.recipeId in ?1")
    List<Long> findBestReceived(List<Long> ids, Pageable pageable);

}
