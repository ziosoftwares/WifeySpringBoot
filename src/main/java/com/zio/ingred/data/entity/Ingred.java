package com.zio.ingred.data.entity;


import com.zio.recipe.data.entity.Allergen;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ingred {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String imgUrl;
    @Enumerated
    @Column(nullable = false)
    private Category category;
    @Enumerated
    @Column(nullable = false)
    private Units unit;
    @Enumerated
    @Column(nullable = false)
    private Allergen allergen = Allergen.NONE;

    @Embedded
    private Nutrition nutrition;

    public Ingred(Long id) {
        this.id = id;
    }
}
