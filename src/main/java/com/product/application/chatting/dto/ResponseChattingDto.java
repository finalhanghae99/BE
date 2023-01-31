package com.product.application.chatting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class ResponseChattingDto {
    private String sellerProfileImageUrl;
    private String buyerProfileImageUrl;
    private String seller;
    private String buyer;
    private String campingName;
    private String lastChatMessage;
    private LocalDateTime lastSendDate;

    @Builder
    public ResponseChattingDto(String sellerProfileImageUrl, String buyerProfileImageUrl, String seller, String buyer, String campingName, String lastChatMessage, LocalDateTime lastSendDate) {
        this.sellerProfileImageUrl = sellerProfileImageUrl;
        this.buyerProfileImageUrl = buyerProfileImageUrl;
        this.seller = seller;
        this.buyer = buyer;
        this.campingName = campingName;
        this.lastChatMessage = lastChatMessage;
        this.lastSendDate = lastSendDate;

    }
}
