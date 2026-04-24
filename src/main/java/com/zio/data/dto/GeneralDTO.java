package com.zio.data.dto;

import com.zio.ingred.data.Units;
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
    private String imgUrl;
    private String authorName;

    public GeneralDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GeneralDTO(Long id, String name, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public GeneralDTO(Long id, String name, String imgUrl, String authorName) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.authorName = authorName;
    }

    @Override
    public int compareTo(@NotNull GeneralDTO o) {
        return Long.compare(id, o.getId());
    }
}
