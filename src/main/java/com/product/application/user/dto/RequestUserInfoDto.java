package com.product.application.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestUserInfoDto {
    private String nickname;
    private String profileImageUrl;
}
