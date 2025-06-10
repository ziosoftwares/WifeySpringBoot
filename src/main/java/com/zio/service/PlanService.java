package com.zio.service;

import com.zio.util.ZioException;
import com.zio.data.dto.DayPlan;
import com.zio.data.entity.meal.Meal;
import com.zio.data.entity.Preferences;
import com.zio.data.Allergen;
import com.zio.repo.MealRepo;
import com.zio.repo.PreferenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanService {

    @Autowired
    private MealRepo mealRepo;
    @Autowired
    private PreferenceRepo preferenceRepo;

    final private String tag = getClass().getName();

    public List<Meal> getMealsByPrefs(Long userId) throws ZioException {

        Preferences prefs = preferenceRepo.findById(userId).orElseThrow(() -> new ZioException(tag + ".NO_PREFS"));
        ArrayList<Allergen> allergenList = new ArrayList<>();
        allergenList.add(Allergen.DUMMY_ONLY_FOR_INTERNAL_USAGE); //mandatory to avoid sql exception of invalid query
        allergenList.addAll(prefs.getAllergens());

        List<Long> filteredIds = mealRepo.findMealByPreference(new ArrayList<>(prefs.getDiets()), new ArrayList<>(prefs.getCuisines()), allergenList);
        if (filteredIds.isEmpty()) throw new ZioException(tag + ".TOO_NARROW_PREFS");
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

    public List<DayPlan> makePlanForDays(int days, Long userId) throws ZioException {

        /// just sequentially delivering now

        List<Meal> mealsByPref = getMealsByPrefs(userId);
        List<Meal> meals = sortByMatchScore(userId, mealsByPref);

        List<DayPlan> result = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            result.add(new DayPlan(meals.get(i * 3), meals.get(i * 3 + 1), meals.get(i * 3 + 2)));
        }

        return result;
    }


}
