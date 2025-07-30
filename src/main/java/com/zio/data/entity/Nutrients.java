package com.zio.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Nutrients {

    @Column(nullable = false, scale = 2)
    private Double calories, fat, protein, carbs;

    public Nutrients(Nutrients source) {
        this.calories = source.calories;
        this.fat = source.fat;
        this.protein = source.protein;
        this.carbs = source.carbs;
    }
}

