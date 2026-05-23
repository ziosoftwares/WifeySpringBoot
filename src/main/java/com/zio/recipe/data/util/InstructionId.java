package com.zio.recipe.data.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InstructionId implements Serializable {
    private Integer id;
    private Long recipeId;

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof InstructionId that)) return false;

        return id.equals(that.id) && recipeId.equals(that.recipeId);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + recipeId.hashCode();
        return result;
    }
}
