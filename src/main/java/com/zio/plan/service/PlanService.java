package com.zio.plan.service;

import com.zio.common.data.RecipeInfo;
import com.zio.common.data.api.Error;
import com.zio.plan.client.AiClient;
import com.zio.plan.data.*;
import com.zio.plan.data.entity.DayPlan;
import com.zio.plan.data.entity.Plan;
import com.zio.plan.repo.PlanRepo;
import com.zio.recipe.service.RecipeQueryService;
import com.zio.common.util.SessionManager;
import com.zio.common.util.ZioException;
import com.zio.user.repo.PreferenceRepo;
import com.zio.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanService {

    @Autowired
    private PlanRepo planRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private RecipeQueryService recipeService;
    @Autowired
    private PreferenceRepo preferenceRepo;
    @Autowired
    private AiClient aiClient;

    final private String tag = getClass().getName();

    public void savePlan(List<DayPlanDTO> dayPlanDTO, String title) throws ZioException {
        Plan plan = new Plan();
        plan.setTitle(title);
        plan.setDayPlans(dayPlanDTO.stream().map(it -> new DayPlan(plan, it.getDay(), it.getBreakfast(), it.getLunch(), it.getDinner())).toList());
        plan.setAuthorId(SessionManager.getUserId());
        plan.setAuthorName(userService.getUserById(plan.getAuthorId()).getUserName());

        plan.getDayPlans().forEach(it -> it.setPlanId(plan));
        plan.setImgUrls(getImageUrls(
                plan.getDayPlans().getFirst().getBreakfast().getFirst(),
                plan.getDayPlans().getFirst().getLunch().getFirst(),
                plan.getDayPlans().getFirst().getDinner().getFirst()));
        planRepo.save(plan);
    }

    private List<String> getImageUrls(Long id1, Long id2, Long id3) throws ZioException {
        List<String> list = new ArrayList<>();
        list.add(recipeService.getRecipeImgUrl(id1));
        list.add(recipeService.getRecipeImgUrl(id2));
        list.add(recipeService.getRecipeImgUrl(id3));
        return list;
    }

    public List<DayPlanFull> makePlanForDays(Integer days) throws ZioException {

        List<RecipeInfo> recipes = recipeService.getRecipesByPreference(SessionManager.getUserId());
        var dayPlanRaw = aiClient.generate(new PlanRequest(days, recipes)).block().getDayPlans();

        return getheridiot(dayPlanRaw);
    }

    public List<PlanDTO> getAllPlans() {
        return planRepo.findAll().stream().map(PlanDTO::new).peek(it -> it.setDayPlans(null)).toList();
    }

    public PlanFull getPlan(Long id) throws ZioException {
        var plan = planRepo.findById(id).orElseThrow(() -> new ZioException(new Error(404, 6, "NO_SUCH_PLAN")));
        var plan1 = new PlanFull(plan);
        plan1.setDayPlans(from(plan.getDayPlans()));
        return plan1;
    }

    private List<DayPlanFull> from(List<DayPlan> dayPlanDTOS) throws ZioException {
        List<DayPlanFull> result = new ArrayList<>();
        for (DayPlan dayPlan : dayPlanDTOS) {
            var day = new DayPlanFull();
            day.setDay(dayPlan.getDay());
            for (Long l : dayPlan.getBreakfast()) {
                day.getBreakfast().add(recipeService.getRecipeDTO(l));
            }
            for (Long l : dayPlan.getLunch()) {
                day.getLunch().add(recipeService.getRecipeDTO(l));

            }
            for (Long l : dayPlan.getDinner()) {
                day.getDinner().add(recipeService.getRecipeDTO(l));

            }
            result.add(day);
        }
        return result;
    }

    private List<DayPlanFull> getheridiot(List<DayPlanDTO> dayPlanDTOS) throws ZioException {
        List<DayPlanFull> result = new ArrayList<>();
        for (DayPlanDTO dayPlan : dayPlanDTOS) {
            var day = new DayPlanFull();
            day.setDay(dayPlan.getDay());
            for (Long l : dayPlan.getBreakfast()) {
                day.getBreakfast().add(recipeService.getRecipeDTO(l));
            }
            for (Long l : dayPlan.getLunch()) {
                day.getLunch().add(recipeService.getRecipeDTO(l));

            }
            for (Long l : dayPlan.getDinner()) {
                day.getDinner().add(recipeService.getRecipeDTO(l));

            }
            result.add(day);
        }
        return result;
    }
}
