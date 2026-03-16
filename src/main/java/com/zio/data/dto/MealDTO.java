package com.zio.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealDTO {

    private Long id;
    private String name;
    private GeneralDTO main;
    private String imgUrl;
    private List<GeneralDTO> sides = new ArrayList<>();
}
