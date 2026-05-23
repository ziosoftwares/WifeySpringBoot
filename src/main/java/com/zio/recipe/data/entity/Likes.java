package com.zio.recipe.data.entity;

import com.zio.recipe.data.util.LikeId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(LikeId.class)
public class Likes {

    @Id
    private Long authorId;
    @Id
    @ManyToOne
    @JoinColumn(name = "recipeId", nullable = false)
    private Reception recipeId;
}
