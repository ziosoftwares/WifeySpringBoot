package com.zio.ingred.data;


import com.zio.ingred.data.entity.Category;
import com.zio.ingred.data.entity.Nutrition;
import com.zio.ingred.data.entity.Units;
import com.zio.recipe.data.entity.Allergen;
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
    private Category category;
    private Units unit;
    private Allergen allergen;
    private Nutrition nutrition;

    public IngredDTO(Long id) {
        this.id = id;
    }

    public IngredDTO(Long id, String name, String imgUrl, Category category, Units unit) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.category = category;
        this.unit = unit;
    }
}
