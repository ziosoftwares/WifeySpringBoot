package com.zio.recipe.data;

import com.zio.ingred.data.IngredDTO;
import com.zio.ingred.data.entity.Nutrition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredQuantityDTO {

    private IngredDTO ingred;
    private Double quantity;

    public Nutrition calculateNutrition() {
        Double multiplier = quantity;
        switch (ingred.getUnit()) {
            case GRAM, MILLI -> multiplier /= 100;
        }
        return ingred.getNutrition().multiply(multiplier);
    }

}
