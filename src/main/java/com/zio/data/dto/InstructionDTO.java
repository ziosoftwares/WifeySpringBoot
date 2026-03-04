package com.zio.data.dto;

import com.zio.data.entity.RecipeDetails;
import com.zio.data.utils.InstructionId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructionDTO {

    private Integer id;
    private Integer duration;
    private String instruction;

}
