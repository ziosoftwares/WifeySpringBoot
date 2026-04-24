package com.zio.plan.service;

import com.zio.plan.client.AiClient;
import com.zio.data.api.Error;
import com.zio.data.dto.DayPlanDTO;
import com.zio.data.dto.GeneralDTO;
import com.zio.data.dto.MealsRequest;
import com.zio.recipe.data.util.ObjectMapper;
import com.zio.util.SessionManager;
import com.zio.util.ZioException;
import com.zio.recipe.data.entity.Meal;
import com.zio.user.data.entity.Preferences;
import com.zio.recipe.data.entity.Allergen;
import com.zio.recipe.repo.MealRepo;
import com.zio.user.repo.PreferenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanService {

    @Autowired
    private MealRepo mealRepo;
    @Autowired
    private PreferenceRepo preferenceRepo;
    @Autowired
    private AiClient aiClient;

    final private String tag = getClass().getName();

    public List<Meal> getMealsByPrefs(Long userId) throws ZioException {

        Preferences prefs = preferenceRepo.findById(userId).orElseThrow(() -> new ZioException(new Error(404, "NO_PREFS", 5)));
        ArrayList<Allergen> allergenList = new ArrayList<>();
        allergenList.add(Allergen.NONE); //mandatory to avoid sql invalid query exception
        allergenList.addAll(prefs.getAllergens());

        List<Long> filteredIds = mealRepo.findMealByPreference(new ArrayList<>(prefs.getDiets()), new ArrayList<>(prefs.getCuisines()), allergenList);
        return mealRepo.findAllById(filteredIds);
    }

    public List<Meal> sortByMatchScore(Long userId, List<Meal> meals) {

        meals = meals.stream().sorted((o1, o2) -> Integer.compare(findMatchScore(userId, o1), findMatchScore(userId, o2)))
                .limit(30).collect(Collectors.toList());
        while (meals.size() < 30) meals.addAll(meals);
        return meals;
    }

    public Integer findMatchScore(Long userId, Meal meal) {
        return 0;
    }

    public Mono<List<DayPlanDTO>> makePlanForDays(int days) throws ZioException {
        Long userId = SessionManager.getUserId();
        List<Meal> meals = getMealsByPrefs(userId);
        List<GeneralDTO> mealOptions = meals.stream().limit(30L).map(ObjectMapper::toGenDTO).toList();
//        List<Meal> meals = sortByMatchScore(userId, mealsByPref);

        return generatePlan(new MealsRequest(days, mealOptions));


    }

    private Mono<List<DayPlanDTO>> generatePlan(MealsRequest request) {
        return aiClient.generate(request).flatMap(result -> {
            List<DayPlanDTO> plan = new ArrayList<>();
            for (int i = 0; i < request.getDays(); i++) {
                plan.add(new DayPlanDTO(
                        findDTO(request.getOptions(), result.get(i * 3)),
                        findDTO(request.getOptions(), result.get(i * 3 + 1)),
                        findDTO(request.getOptions(), result.get(i * 3 + 2))
                ));
            }
            return Mono.just(plan);
        });
    }

    private GeneralDTO findDTO(List<GeneralDTO> options, long id) {
        return options.get(
                Collections.binarySearch(options, new GeneralDTO(id, ""))
        );
    }

}
