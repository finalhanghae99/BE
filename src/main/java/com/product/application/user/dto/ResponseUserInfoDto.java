package com.product.application.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseUserInfoDto {
    private Long userId;
    private String nickname;
    private String profileImageUrl;
}
