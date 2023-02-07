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
    private Long reservationId;

    @Builder
    public ResponseChatReservationDto(String imageUrl, String campingName, Long price, Long reservationId){
        this.imageUrl = imageUrl;
        this.campingName = campingName;
        this.price = price;
        this.reservationId = reservationId;

    }
}
