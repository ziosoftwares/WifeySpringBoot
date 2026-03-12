package com.zio.data.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private int status = 500;
    private String message;
    private int code;

    public Error(String message, int code) {
        this.code = code;
        this.message = message;
    }
}
