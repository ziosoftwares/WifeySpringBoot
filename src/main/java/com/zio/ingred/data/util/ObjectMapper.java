package com.zio.ingred.data.util;

import com.zio.common.data.dto.GeneralDTO;
import com.zio.ingred.data.entity.Ingred;

public class ObjectMapper {

    public static GeneralDTO toGenDTO(Ingred ingred) {
        return new GeneralDTO(ingred.getId(), ingred.getName(), ingred.getImgUrl());
    }

}
