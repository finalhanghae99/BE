package com.product.application.user.controller;

import com.product.application.user.dto.ResponseUserCampingInfoDto;
import com.product.application.common.ResponseMessage;
import com.product.application.user.dto.ResponseUserInfoDto;
import com.product.application.user.jwt.JwtUtil;
import com.product.application.user.service.UserInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserInformationController {
    private final UserInformationService userInformationService;

    @CrossOrigin(originPatterns = "http://localhost:3000", exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @GetMapping("/mypage")
    public ResponseMessage<?> userInfo(HttpServletRequest request) {
        ResponseUserInfoDto responseUserInfoDto = userInformationService.userInfo(request);
        return new ResponseMessage<>("Success", 200, responseUserInfoDto);
    }

    @CrossOrigin(originPatterns = "http://localhost:3000", exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @GetMapping("/mypage/camping")
    public ResponseMessage<?> userCampingInfo(HttpServletRequest request){
        ResponseUserCampingInfoDto responseUserCampingInfo = userInformationService.userCampingInfo(request);
        return new ResponseMessage<>("Success", 200, responseUserCampingInfo);
    }

}
