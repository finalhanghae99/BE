package com.product.application.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestUserInfoDto {
    private String nickname;
    private String profileImageUrl;

}
