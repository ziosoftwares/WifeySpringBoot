package com.zio.ingred.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Nutrition {

    @Column(nullable = false, scale = 2)
    private Double calories = 0.0, fat = 0.0, protein = 0.0, carbs = 0.0;

    public Nutrition multiply(Double multiplier) {
        return new Nutrition(calories * multiplier, fat * multiplier, protein * multiplier, carbs * multiplier);
    }

    public Nutrition add(Nutrition another) {
        return new Nutrition(calories + another.calories, fat + another.fat, protein + another.protein, carbs + another.carbs);
    }

}

