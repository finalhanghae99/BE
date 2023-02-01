package com.product.application.chatting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResponseChatRoomDto {
    private String roomId;

    @Builder
    public ResponseChatRoomDto(String roomId){
        this.roomId = roomId;


    }
}
