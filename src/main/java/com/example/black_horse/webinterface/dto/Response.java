package com.example.black_horse.webinterface.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.black_horse.webinterface.dto.ResultStatus.PAYMENT_CREATE_SUCCESS;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private Integer code;
    private String message;
    private T data;

    private Response(ResultStatus resultStatus, T data) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
        this.data = data;
    }

    public static Response<Void> success() {
        return new Response<Void>(ResultStatus.PAYMENT_CREATE_SUCCESS, null);
    }

    public static Response<Void> success(ResultStatus resultStatus) {
        return new Response<Void>(resultStatus, null);
    }

    public static <T> Response<T> success(T data) {
        return new Response<T>(PAYMENT_CREATE_SUCCESS, data);
    }

    public static <T> Response<T> success(T data, ResultStatus resultStatus) {
        return new Response<T>(resultStatus, data);
    }

    public static <T> Response<T> failure(ResultStatus resultStatus) {
        return new Response<T>(resultStatus, null);
    }

}
