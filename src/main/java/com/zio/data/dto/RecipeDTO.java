package com.zio.data.dto;

import com.zio.data.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static RecipeDTO create(Recipe recipe) {
        return new RecipeDTO(recipe.getId(), recipe.getName(), recipe.getImgUrl(), recipe.getAuthor().getUserName(), recipe.getAuthor().getId(), null, null);
    }

}

