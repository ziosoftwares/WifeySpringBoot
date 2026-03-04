package com.zio.data.dto;

import com.zio.data.entity.Ingred;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredQuantityDTO {

    private Ingred ingred;
    private Double quantity;

}
