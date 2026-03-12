package com.zio.data.entity;


import com.zio.data.dto.GeneralDTO;
import com.zio.wifey.data.recipe.FoodType;
import com.zio.wifey.data.recipe.Units;
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
    private FoodType type;

    @Embedded
    private Nutrients nutrients;

    @Enumerated
    @Column(nullable = false)
    private Units unit;

    public GeneralDTO makeGenDTO() {
        return new GeneralDTO(id, name, unit, imgUrl, null);
    }
}
