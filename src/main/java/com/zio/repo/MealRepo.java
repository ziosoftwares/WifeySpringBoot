package com.zio.repo;

import com.zio.data.dto.IdNameDTO;
import com.zio.data.entity.Meal;
import com.zio.data.Allergen;
import com.zio.data.Cuisine;
import com.zio.data.Diet;
import com.zio.data.utils.IngredQuantityDTO;
import org.springframework.data.domain.PageRequest;
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


    @Query("select new com.zio.data.dto.IdNameDTO(i.id, i.name) from Meal i where lower(i.name) like lower(?1)")
    List<IdNameDTO> findMealLike(String s, PageRequest of);

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
