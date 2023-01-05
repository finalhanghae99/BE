package com.product.application.common.exception;

import com.product.application.common.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomException.class})
    protected ResponseEntity handleCustomException(CustomException ex) {
        return new ResponseEntity(new ResponseMessage(ex.getErrorCode().getMsg(), ex.getErrorCode().getStatusCode(), ex.getErrorCode())
                , HttpStatus.OK);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        StringBuilder errMessage = new StringBuilder();

        for (FieldError error : result.getFieldErrors()) {
            errMessage.append("[")
                    .append(error.getField())
                    .append("] ")
                    .append(":")
                    .append(error.getDefaultMessage());
        }

        return new ResponseEntity<>(new ResponseMessage(errMessage.toString(), 404, "error"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity handleServerException(Exception ex) {
        return new ResponseEntity(new ResponseMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "error")
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}