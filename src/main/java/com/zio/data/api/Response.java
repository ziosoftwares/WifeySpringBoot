package com.zio.data.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    private T body;
    private int statusCode;
    private Error error;

    public Response(T body, HttpStatus statusCode) {
        this.body = body;
        this.statusCode = statusCode.value();
    }

    public Response(HttpStatus statusCode, Error error) {
        this.error = error;
        this.statusCode = statusCode.value();
    }

    public static Response error(HttpStatus httpStatus, String message, int errorCode) {
        Error error = new Error(message, errorCode);
        return new Response(httpStatus, error);
    }

    public static <T> Response<T> ok(T body) {
        return new Response<>(body, HttpStatus.OK);
    }

    public boolean isSuccess() {
        return statusCode >= 200 && statusCode <= 299;
    }

    public T body() {
        return body;
    }


}
