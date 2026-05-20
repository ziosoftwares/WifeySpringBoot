package com.zio.user.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    private Long userId;
    private String userName = "NA";
    private Integer recipes = 0;

    public Author(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
