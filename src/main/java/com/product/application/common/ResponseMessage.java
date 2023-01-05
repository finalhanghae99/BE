package com.product.application.common;

import com.product.application.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ResponseMessage<T> {
    private String msg;
    private int statusCode;
    private T data;

    public ResponseMessage(String msg, int statusCode, T data) {
        this.msg = msg;
        this.statusCode = statusCode;
        this.data = data;
    }

    public ResponseMessage(ErrorCode errorCode, T data) {
        this.msg = errorCode.getMsg();
        this.statusCode = errorCode.getStatusCode();
        this.data = data;
    }
}
