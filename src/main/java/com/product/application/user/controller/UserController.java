package com.product.application.user.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.user.dto.RequestEmailcheckDto;
import com.product.application.user.dto.RequestLoginDto;
import com.product.application.user.dto.RequestNicknamecheckDto;
import com.product.application.user.dto.RequestSignupDto;
import com.product.application.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users/signup")
    public ResponseMessage<?> signup(@RequestBody @Valid RequestSignupDto requestSignupDto){
        userService.signup(requestSignupDto);
        return new ResponseMessage<>("Success", 200, null);
    }

    @PostMapping("/users/login")
    public ResponseMessage<?> login(@RequestBody RequestLoginDto requestLoginDto, HttpServletResponse response){
        userService.login(requestLoginDto, response);
        return new ResponseMessage<>("Success", 200, null);
    }

    @PostMapping("/users/checkemail")
    public ResponseMessage<?> emailcheck(@RequestBody RequestEmailcheckDto requestEmailcheckDto){
        userService.emailcheck(requestEmailcheckDto);
        return new ResponseMessage<>("Success", 200, null);
    }

    @PostMapping("/users/checknickname")
    public ResponseMessage<?> nicknamecheck(@RequestBody RequestNicknamecheckDto requestNicknamecheckDto){
        userService.nicknamecheck(requestNicknamecheckDto);
        return new ResponseMessage<>("Success", 200, null);
    }
}
