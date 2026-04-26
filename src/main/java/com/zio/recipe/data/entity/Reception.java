package com.zio.recipe.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reception {

    @Id
    private Long recipeId;
    private Long likesCount = 0L;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipeId", orphanRemoval = true)
    private List<Likes> likes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipeId", orphanRemoval = true)
    private List<Comment> comments;

    public Reception(Long recipeId) {
        this.recipeId = recipeId;
    }
}
