package com.product.application.chatting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResponseChatReservationDto {
    private String imageUrl;
    private String campingName;
    private Long price;

    @Builder
    public ResponseChatReservationDto(String imageUrl, String campingName, Long price){
        this.imageUrl = imageUrl;
        this.campingName = campingName;
        this.price = price;

    }
}
