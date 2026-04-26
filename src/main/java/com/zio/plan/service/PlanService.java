package com.zio.plan.service;

import com.zio.plan.client.AiClient;
import com.zio.plan.data.entity.Plan;
import com.zio.plan.repo.PlanRepo;
import com.zio.recipe.repo.RecipeRepo;
import com.zio.recipe.service.RecipeQueryService;
import com.zio.common.util.SessionManager;
import com.zio.common.util.ZioException;
import com.zio.plan.data.entity.Meal;
import com.zio.plan.repo.MealRepo;
import com.zio.user.repo.PreferenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    @Autowired
    private MealRepo mealRepo;
    @Autowired
    private PlanRepo planRepo;
    @Autowired
    private RecipeRepo recipeRepo;

    @Autowired
    private RecipeQueryService recipeService;

    @Autowired
    private PreferenceRepo preferenceRepo;
    @Autowired
    private AiClient aiClient;

    final private String tag = getClass().getName();

    public Long savePlan(Plan plan) throws ZioException {
        plan.getDayPlans().forEach(dayPlan -> {
            dayPlan.setBreakfast(checkMeal(dayPlan.getBreakfast()));
            dayPlan.setLunch(checkMeal(dayPlan.getLunch()));
            dayPlan.setDinner(checkMeal(dayPlan.getDinner()));
            dayPlan.setPlan(plan);
        });
        plan.setAuthorId(SessionManager.getUserId());
        return planRepo.save(plan).getId();
    }

    private Meal checkMeal(Meal meal) {
        return mealRepo.findByMainAndSide1AndSide2(meal.getMain(), meal.getSide1(), meal.getSide2())
                .orElseGet(() -> {
                    meal.setName(buildMealName(meal));
                    return mealRepo.save(meal);
                });
    }

    public String buildMealName(Meal meal) {
        StringBuilder sb = new StringBuilder();
        append(sb, recipeRepo.findNameById(meal.getMain()));
        append(sb, recipeRepo.findNameById(meal.getSide1()));
        append(sb, recipeRepo.findNameById(meal.getSide2()));
        return sb.toString();
    }

    private void append(StringBuilder sb, String name) {
        if (name == null || name.isBlank()) return;
        if (!sb.isEmpty()) sb.append(" | ");
        sb.append(name);
    }


    public Plan makePlanForDays(Integer days) {
        List<>
        return null;
    }
}
