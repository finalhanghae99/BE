package com.product.application.user.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.user.dto.ResponseUserInfo;
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

    @CrossOrigin(originPatterns = "http://localhost:3000")
    @GetMapping("/mypage")
    public ResponseMessage<?> userInfo(HttpServletRequest request) {
        ResponseUserInfo responseUserInfo = userInformationService.userInfo(request);
        return new ResponseMessage<>("Success", 200, responseUserInfo);
    }
}
