package com.product.application.chatting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResponseNicknameDto {
    private String nickname;
    private String roomId;

    @Builder
    public ResponseNicknameDto(String nickname, String roomId) {
        this.nickname = nickname;
        this.roomId = roomId;

    }



}
