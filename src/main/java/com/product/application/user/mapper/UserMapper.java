package com.product.application.user.mapper;

import com.product.application.user.dto.SignupRequestDto;
import com.product.application.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public Users toUser(SignupRequestDto signupRequestDto){
        return Users.builder()
                .useremail(signupRequestDto.getUseremail())
                .nickname(signupRequestDto.getNickname())
                .password(signupRequestDto.getPassword())
                .profileImageUrl(signupRequestDto.getProfileImageUrl())
                .build();
    }
}
