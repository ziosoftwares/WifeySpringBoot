package com.zio.recipe.repo;

import com.zio.recipe.data.entity.Likes;
import com.zio.recipe.data.entity.Reception;
import com.zio.recipe.data.entity.RecipeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReceptionRepo extends JpaRepository<Reception, Long> {

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END " +
            "FROM Reception r JOIN r.likes l " +
            "WHERE r.recipeId = ?1 AND l.authorId = ?2")
    Boolean isLikedBy(Long recipeId, Long authorId);

    @Query("select r.likesCount from Reception r where r.recipeId=?1")
    Long findLikesCountByRecipeId(Long recipeId);
}
