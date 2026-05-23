package com.zio.ingred.data.util;

import com.zio.common.data.GeneralDTO;
import com.zio.ingred.data.IngredDTO;
import com.zio.ingred.data.entity.Ingred;

public class ObjectMapper {

    public static GeneralDTO toGenDTO(Ingred ingred) {
        return new GeneralDTO(ingred.getId(), ingred.getName(), ingred.getImgUrl());
    }
    public static IngredDTO toDTO(Ingred ingred) {
        return new IngredDTO(ingred.getId(), ingred.getName(), ingred.getImgUrl(), ingred.getCategory(), ingred.getUnit(), ingred.getAllergen());
    }

}
