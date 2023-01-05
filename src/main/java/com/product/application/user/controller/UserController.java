package com.product.application.user.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.user.dto.EmailcheckRequestDto;
import com.product.application.user.dto.LoginRequestDto;
import com.product.application.user.dto.SignupRequestDto;
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
    public ResponseMessage<?> signup(@RequestBody @Valid SignupRequestDto signupRequestDto){
        userService.signup(signupRequestDto);
        return new ResponseMessage<>("Success", 200, null);
    }

    @PostMapping("/users/login")
    public ResponseMessage<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        userService.login(loginRequestDto, response);
        return new ResponseMessage<>("Success", 200, null);
    }

    @PostMapping("/users/checkemail")
    public ResponseMessage<?> emailcheck(@RequestBody EmailcheckRequestDto emailcheckRequestDto){
        userService.emailcheck(emailcheckRequestDto);
        return new ResponseMessage<>("Success", 200, null);
    }
}
