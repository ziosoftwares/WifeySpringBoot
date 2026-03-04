package com.zio.data.entity;

import com.zio.data.utils.IngredQuantityId;
import com.zio.data.utils.InstructionId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(InstructionId.class)
public class Instruction {

    @Id
    private Integer id;
    private Integer duration;
    private String instruction;

    @Id
    @ManyToOne
    @JoinColumn(name = "recipeId", nullable = false)
    private RecipeDetails details;

}
