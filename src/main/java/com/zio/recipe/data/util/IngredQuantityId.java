package com.zio.recipe.data.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IngredQuantityId implements Serializable {
    private Long recipeId;
    private Long ingredId;
    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof IngredQuantityId that)) return false;

        return ingredId.equals(that.ingredId) && recipeId.equals(that.recipeId);
    }

    @Override
    public int hashCode() {
        int result = ingredId.hashCode();
        result = 31 * result + recipeId.hashCode();
        return result;
    }
}
