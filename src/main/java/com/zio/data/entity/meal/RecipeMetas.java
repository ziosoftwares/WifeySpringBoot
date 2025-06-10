package com.zio.data.entity.meal;


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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer maxTime;

    @Enumerated
    private Difficulty difficulty;

    @Enumerated
    private Diet diet;
    @Enumerated
    private Cuisine cuisine;

    @ElementCollection
    @Enumerated
    @JoinTable(name = "RecipeAllergens",
            joinColumns = @JoinColumn(name = "metaId"))
    private Set<Allergen> allergens;

}
