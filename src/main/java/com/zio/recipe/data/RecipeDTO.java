package com.zio.recipe.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {

    private Long id;
    private String name;
    private String imgUrl;

    private String authorName;
    private Long authorId;

    private RecipeMetasDTO metas;
    private RecipeDetailsDTO details;

    private List<IngredQuantityDTO> ingredQuan;

    private Long likesCount;
    private boolean liked;

}

