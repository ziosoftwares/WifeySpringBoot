package com.zio.recipe.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructionDTO {

    private Integer id;         //step count
    private Integer duration;   //in mins
    private String instruction;

}
