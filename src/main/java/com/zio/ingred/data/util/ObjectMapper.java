package com.zio.ingred.data.util;

import com.zio.data.dto.GeneralDTO;
import com.zio.ingred.data.entity.Ingred;
import com.zio.recipe.data.util.IngredQuantityDTO;

public class ObjectMapper {

    public static GeneralDTO toGenDTO(Ingred ingred) {
        return new GeneralDTO(ingred.getId(), ingred.getName(), ingred.getImgUrl());
    }

}
