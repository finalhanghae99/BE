package com.product.application.chatting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseChatNicknameDto {
    private String seller;
    private String buyer;


    @Builder
    public ResponseChatNicknameDto(String seller, String buyer) {
        this.seller = seller;
        this.buyer = buyer;
    }
}
