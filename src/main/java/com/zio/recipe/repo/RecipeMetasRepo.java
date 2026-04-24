package com.zio.recipe.repo;

import com.zio.recipe.data.entity.Recipe;
import com.zio.recipe.data.entity.RecipeDetails;
import com.zio.recipe.data.entity.RecipeMetas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecipeMetasRepo extends JpaRepository<RecipeMetas, Long> {

}
