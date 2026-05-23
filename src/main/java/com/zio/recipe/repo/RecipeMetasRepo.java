package com.zio.recipe.repo;

import com.zio.recipe.data.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public interface RecipeMetasRepo extends JpaRepository<RecipeMetas, Long>, JpaSpecificationExecutor<RecipeMetas> {

    @Query(value = "SELECT * FROM recipe_metas m " +
            "WHERE m.diet <= ?1 " +
            "AND m.cuisine IN ?2 " +
            "AND (m.allergens & ?3) <= 1 "+
            "LIMIT 50",
            nativeQuery = true)
    List<RecipeMetas> findByPreference(Diet diet, List<Integer> cuisinesOrdinals, Long allergenMask);
}
