package com.product.application.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseUserNicknameDto {
    private String nickname;

    public ResponseUserNicknameDto(String nickname) {
        this.nickname = nickname;
    }
}
