package com.zio.plan.repo;

import com.zio.plan.data.entity.Meal;
import com.zio.recipe.data.entity.Allergen;
import com.zio.recipe.data.entity.Cuisine;
import com.zio.recipe.data.entity.Diet;
import com.zio.recipe.data.util.IngredQuantityDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MealRepo extends JpaRepository<Meal, Long> {

    @Query("SELECT m.id FROM Meal m WHERE m.main = ?1 AND m.side1 = ?2 AND m.side2 = ?3")
    Long findIdByComponents(Long main, Long side1, Long side2);

    Optional<Meal>I findByMainAndSide1AndSide2(Long main, Long side1, Long side2);

    List<Meal> findByNameLike(String name, Pageable pageable);

    @Query(value = """
                SELECT *
                FROM meal m
                JOIN meal_sides ms ON m.id = ms.meal_id
                WHERE m.main_id = :mainId
                AND ms.side_id IN (:sideIds)
                GROUP BY m.id
                HAVING COUNT(ms.side_id) = :sideCount
            """, nativeQuery = true)
    Optional<Meal> findMealByRecipeIds(Long mainId, List<Long> sideIds, int sideCount);


    @Query(value = """
            SELECT DISTINCT m.*
            FROM meal m
            JOIN recipe r ON m.main_id = r.id
            JOIN recipe_metas rm ON r.id = rm.id
            LEFT JOIN recipe_allergens ra ON ra.recipe_id = rm.id
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



    @Query(value = """
            SELECT ingred_id AS ingredId, SUM(total_quantity) AS quantity
                            FROM (
                                -- copy both SELECTs from above here
                                -- ingredients from main dishes
                            SELECT q.ingred_id, SUM(q.quantity) AS total_quantity
                            FROM meal m
                            JOIN recipe r ON m.main_id = r.id
                            JOIN recipe_details d ON r.id = d.id
                            JOIN ingred_quantity q ON q.recipe_id = d.id
                            WHERE m.id IN (?1)
                            GROUP BY q.ingred_id
            
                            UNION ALL
            
                            -- ingredients from side dishes
                            SELECT q.ingred_id, SUM(q.quantity) AS total_quantity
                            FROM meal m
                            JOIN meal_sides s ON s.meal_id = m.id
                            JOIN recipe r ON s.side_id = r.id
                            JOIN recipe_details d ON r.id = d.id
                            JOIN ingred_quantity q ON q.recipe_id = d.id
                            WHERE m.id IN (?1)
                            GROUP BY q.ingred_id
                            ) AS combined
                            GROUP BY ingred_id;
            """, nativeQuery = true)
    List<IngredQuantityDTO> getIngreds(List<Long> mealIds);
}
