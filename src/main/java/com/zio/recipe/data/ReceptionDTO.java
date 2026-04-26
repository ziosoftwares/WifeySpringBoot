package com.zio.recipe.data;

import com.zio.recipe.data.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceptionDTO {
    private Long likesCount;
    private boolean liked;

    private List<Comment> comments;

    public ReceptionDTO(Long likesCount, boolean liked) {
        this.likesCount = likesCount;
        this.liked = liked;
    }
}
