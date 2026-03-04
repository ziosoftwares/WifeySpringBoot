package com.zio.data.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IngredQuantityId implements Serializable {
    private Long ingred;
    private Long details;

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof IngredQuantityId that)) return false;

        return ingred.equals(that.ingred) && details.equals(that.details);
    }

    @Override
    public int hashCode() {
        int result = ingred.hashCode();
        result = 31 * result + details.hashCode();
        return result;
    }
}
