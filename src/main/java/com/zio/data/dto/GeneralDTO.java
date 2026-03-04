package com.zio.data.dto;

import com.zio.wifey.data.recipe.Units;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralDTO implements Comparable<GeneralDTO> {
    private Long id;
    private String name;

    /// optionals
    private Units unit;

    public GeneralDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int compareTo(@NotNull GeneralDTO o) {
        return Long.compare(id, o.getId());
    }
}
