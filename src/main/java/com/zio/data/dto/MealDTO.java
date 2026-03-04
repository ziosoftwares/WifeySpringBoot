package com.zio.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealDTO {

    private Long id;
    private String name;
    private Long main;
    private Set<Long> sides;
}
