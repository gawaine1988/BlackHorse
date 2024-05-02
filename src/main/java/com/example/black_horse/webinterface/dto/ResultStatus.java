package com.example.black_horse.webinterface.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
public enum ResultStatus {
    PAYMENT_CREATE_SUCCESS(1, "PAYMENT_CREATE_SUCCESS"),
    FIND_PAYMENT_SUCCESS(2, "FIND_PAYMENT_SUCCESS"),
    PAYMENT_CONFIRMATION_SUCCESS(3, "PAYMENT_CONFIRMATION_SUCCESS"),
    COMPENSATE_CREATE_SUCCESS(4, "COMPENSATE_CREATE_SUCCESS"),
    PAYMENT_CREATE_FAIL(101, "PAYMENT_CREATE_FAIL"),
    PAYMENT_NOT_EXIST(102, "PAYMENT_NOT_EXIST"),
    PAYMENT_CONFIRMATION_FAIL(103, "PAYMENT_CONFIRMATION_FAIL"),
    COMPENSATE_CREATE_FAIL(104, "COMPENSATE_CREATE_FAIL");
    /**
     * 业务异常码
     * 1~99 成功
     * 101~199 失败
     */
    private Integer code;

    private String message;

    ResultStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
