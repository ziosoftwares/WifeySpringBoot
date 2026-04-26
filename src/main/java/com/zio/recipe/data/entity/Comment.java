package com.zio.recipe.data.entity;

import com.zio.recipe.data.util.LikeId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(LikeId.class)
public class Comment {

    @Id
    private Long authorId;
    private String comment;

    @Id
    @ManyToOne
    @JoinColumn(name = "recipeId", nullable = false)
    private Reception recipeId;
}
