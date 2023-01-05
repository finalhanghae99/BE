package com.product.application.user.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.user.dto.SignupRequestDto;
import com.product.application.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseMessage<?> signup(@RequestBody @Valid SignupRequestDto signupRequestDto){
        userService.signup(signupRequestDto);
        return new ResponseMessage<>("Success", 200, null);
    }
}
