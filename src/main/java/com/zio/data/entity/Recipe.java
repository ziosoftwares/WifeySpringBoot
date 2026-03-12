package com.zio.data.entity;

import com.zio.data.dto.GeneralDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String imgUrl;

    @ManyToOne(optional = false)
    private User author;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "recipe", orphanRemoval = true, optional = false)
    private RecipeMetas metas;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "recipe", orphanRemoval = true, optional = false)
    private RecipeDetails details;

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Recipe recipe)) return false;

        return id.equals(recipe.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public GeneralDTO makeGeneralDTO() {
        return new GeneralDTO(id, name, null, imgUrl, author.getUserName());
    }
}

