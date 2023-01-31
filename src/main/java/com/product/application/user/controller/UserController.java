package com.product.application.user.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.security.UserDetailsImpl;
import com.product.application.user.dto.*;
import com.product.application.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/usernick")
    public ResponseMessage<?> getnickname(@AuthenticationPrincipal UserDetailsImpl userDetails){
        ResponseUserNicknameDto responseUserNicknameDto = userService.getnickname(userDetails.getUser());
        return new ResponseMessage<>("Success", 200, responseUserNicknameDto);
    }
}
