package com.zio.data.entity;


import java.util.Set;

import com.zio.data.Allergen;
import com.zio.data.Cuisine;
import com.zio.data.Diet;
import com.zio.data.Difficulty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RecipeMetas {

    @Id
    private Long id;
    private Integer duration;
    @Enumerated
    private Difficulty difficulty;
    @Enumerated
    private Diet diet;
    @Enumerated
    private Cuisine cuisine;

    @ElementCollection
    @Enumerated
    @JoinTable(name = "RecipeAllergens",
            joinColumns = @JoinColumn(name = "recipeId"))
    private Set<Allergen> allergens;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Recipe recipe;

}
