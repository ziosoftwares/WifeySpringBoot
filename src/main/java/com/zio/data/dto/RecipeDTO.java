package com.zio.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {

    private Long id;
    private String name;
    private RecipeMetasDTO metaData;
    private RecipeDetailsDTO details;

}

