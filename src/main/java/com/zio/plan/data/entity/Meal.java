package com.zio.plan.data.entity;

import java.util.Set;

import com.zio.recipe.data.entity.Recipe;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"main", "side1", "side2"}
        )
)*/
@Entity
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long main;

    @Column(nullable = false)
    @Nonnull
    private Long side1 = 0L, side2 = 0L;

}
