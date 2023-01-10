package com.product.application.camping.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseCampingLikeStateDto {
    private boolean campingLikeState;
    public ResponseCampingLikeStateDto(boolean campingLikeState){
        this.campingLikeState = campingLikeState;
    }

    public void updateReturnState(boolean campingLikeState){
        this.campingLikeState = campingLikeState;
    }
}
