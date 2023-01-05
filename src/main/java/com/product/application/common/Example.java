package com.product.application.common;

import com.product.application.common.exception.CustomException;
import com.product.application.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Example {


    public ResponseEntity<ResponseMessage> test(){
        //응답 샘플
        TestResponseDto testResponseDto = new TestResponseDto(1L, "test중입니다.");
//        TestResponseDto testResponseDto = Service.create(requestDto);

        //에러 상황
        if(true){
            throw new CustomException(ErrorCode.CONTENT_NOT_FOUND);
        }


        //응답 형태
        //<>안에 집어넣을 데이터의 자료형을 집어넣으면 됩니다.
        ResponseMessage<TestResponseDto> responseMessage = new ResponseMessage<>("반환 성공", 200, testResponseDto);

        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }
    
    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    class TestResponseDto{
        private Long id;
        private String msg;
    }
}
