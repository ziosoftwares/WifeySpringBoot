package com.zio.data.dto;


import com.zio.data.entity.Nutrients;
import com.zio.wifey.data.recipe.FoodType;
import com.zio.wifey.data.recipe.Units;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredDTO {

    private Long id;
    private String name;
    private String imgUrl;
    private FoodType type;
    private Nutrients nutrients;
    private Units unit;
}
