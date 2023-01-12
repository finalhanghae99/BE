package com.product.application.user.mapper;

import com.product.application.user.dto.RequestSignupDto;
import com.product.application.user.dto.ResponseUserInfo;
import com.product.application.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public Users toUser(RequestSignupDto requestSignupDto){
        return Users.builder()
                .useremail(requestSignupDto.getUseremail())
                .nickname(requestSignupDto.getNickname())
                .password(requestSignupDto.getPassword())
                .profileImageUrl(requestSignupDto.getProfileImageUrl())
                .build();
    }

    public ResponseUserInfo toResponseUserInfo(Users users) {
        return ResponseUserInfo.builder()
                .userId(users.getId())
                .nickname(users.getNickname())
                .profileImageUrl(users.getProfileImageUrl())
                .build();
    }
}
