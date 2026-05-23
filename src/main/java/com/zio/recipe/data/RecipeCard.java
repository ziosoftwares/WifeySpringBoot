package com.zio.recipe.data;

import com.zio.recipe.data.entity.Diet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCard {

    private Long id;
    private String name;
    private String imgUrl;

    private String authorName;
    private Long authorId;

    private Integer duration;
    private Diet diet;

    private Long likesCount;
    private boolean liked;

}

