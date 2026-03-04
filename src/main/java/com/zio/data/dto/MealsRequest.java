package com.zio.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealsRequest {

    private Integer days;
    private List<GeneralDTO> options;
}
