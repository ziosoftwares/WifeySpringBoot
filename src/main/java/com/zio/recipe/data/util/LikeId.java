package com.zio.recipe.data.util;

import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LikeId implements Serializable {
    private Long authorId;
    private Long recipeId;

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof LikeId that)) return false;

        return authorId.equals(that.authorId) && recipeId.equals(that.recipeId);
    }

    @Override
    public int hashCode() {
        int result = authorId.hashCode();
        result = 31 * result + recipeId.hashCode();
        return result;
    }
}
