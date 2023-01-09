package com.product.application.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestLoginDto {
    private String useremail;
    private String password;
}
