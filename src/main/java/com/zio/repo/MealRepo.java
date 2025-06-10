package com.zio.repo;

import com.zio.data.entity.meal.Meal;
import com.zio.data.Allergen;
import com.zio.data.Cuisine;
import com.zio.data.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MealRepo extends JpaRepository<Meal, Long> {

//    @Query("select m from Meal m where " +
//            "m.main.metas.diet in ?1 and " +
//            "m.main.metas.cuisine in ?2 and " +
//            "m.main.metas.allergens in ?3")
//    List<Meal> findMealByPreference(List<Diet> diets, List<Cuisine> cuisines,
//                                    List<Allergen> allergens);

    @Query(value = """
    SELECT DISTINCT m.*
    FROM meal m
    JOIN recipe r ON m.main_id = r.id
    JOIN recipe_metas rm ON r.meta_id = rm.id
    LEFT JOIN recipe_allergens ra ON ra.meta_id = rm.id
    WHERE rm.diet IN (?1)
    AND rm.cuisine IN (?2)
    AND (ra.allergens IS NULL OR ra.allergens NOT IN (?3))
    LIMIT 100
    """, nativeQuery = true)
    /**
     * For filtering, careful with allergens param
     @return at-max 100 ids of filtered meals
     @param allergens min size 1
     */
    List<Long> findMealByPreference(@Param("diets") List<Diet> diets,
                                    @Param("cuisines") List<Cuisine> cuisines,
                                    @Param("allergens") List<Allergen> allergens);



}
