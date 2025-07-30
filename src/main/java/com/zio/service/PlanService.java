package com.zio.service;

import com.zio.data.dto.DayPlanDTO;
import com.zio.data.dto.IdNameDTO;
import com.zio.util.ZioException;
import com.zio.data.dto.DayPlan;
import com.zio.data.entity.Meal;
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

    public List<DayPlanDTO> makePlanForDays(int days, Long userId) throws ZioException {

        /// just sequentially delivering now

        List<Meal> mealsByPref = getMealsByPrefs(userId);
        List<Meal> meals = sortByMatchScore(userId, mealsByPref);

        List<DayPlanDTO> result = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            result.add(new DayPlanDTO(
                    new IdNameDTO(meals.get(i * 3).getId(), meals.get(i * 3).getName()),
                    new IdNameDTO(meals.get(i * 3 + 1).getId(), meals.get(i * 3 + 1).getName()),
                    new IdNameDTO(meals.get(i * 3 + 2).getId(), meals.get(i * 3 + 2).getName())
            ));
        }

        return result;
    }


    public DayPlanDTO makeDayPlan(Long userId) throws ZioException {
        List<Meal> mealsByPref = getMealsByPrefs(userId);
        List<Meal> meals = sortByMatchScore(userId, mealsByPref);

        var list = meals.stream().limit(3).map(m -> new IdNameDTO(m.getId(), m.getName())).toList();
        return new DayPlanDTO(list.get(0), list.get(1), list.get(2));
    }
}
