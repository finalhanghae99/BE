package com.product.application.user.controller;

import com.product.application.review.dto.ResponseReviewAllDto;
import com.product.application.review.dto.ResponseReviewOneListDto;
import com.product.application.user.dto.RequestUserInfoDto;
import com.product.application.user.dto.ResponseUserCampingInfoDto;
import com.product.application.common.ResponseMessage;
import com.product.application.user.dto.ResponseUserInfoDto;
import com.product.application.user.jwt.JwtUtil;
import com.product.application.user.service.UserInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserInformationController {
    private final UserInformationService userInformationService;

    @CrossOrigin(origins = {"http://campingzipbeta.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:3000"}, exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @GetMapping("/mypage")
    public ResponseMessage<?> userInfo(HttpServletRequest request) {
        ResponseUserInfoDto responseUserInfoDto = userInformationService.userInfo(request);
        return new ResponseMessage<>("Success", 200, responseUserInfoDto);
    }

    @CrossOrigin(origins = {"http://campingzipbeta.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:3000"}, exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @GetMapping("/mypage/camping")
    public ResponseMessage<?> userCampingInfo(HttpServletRequest request){
        ResponseUserCampingInfoDto responseUserCampingInfo = userInformationService.userCampingInfo(request);
        return new ResponseMessage<>("Success", 200, responseUserCampingInfo);
    }

    @CrossOrigin(origins = {"http://campingzipbeta.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:3000"}, exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @GetMapping("/mypage/update")
    public ResponseMessage<?> userInfoChange(@RequestBody RequestUserInfoDto requestUserInfoDto, HttpServletRequest request){
        ResponseUserInfoDto responseUserInfoDto = userInformationService.userInfoChange(requestUserInfoDto, request);
        return new ResponseMessage<>("Success", 200, responseUserInfoDto);
    }

    @CrossOrigin(origins = {"http://campingzipbeta.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:3000"}, exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @GetMapping("/mypage/review")
    public ResponseMessage<?> userReviewInfo(HttpServletRequest request){
        ResponseReviewOneListDto responseReviewOneListDto = userInformationService.userReviewInfo(request);
        return new ResponseMessage<>("Success", 200, responseReviewOneListDto);
    }
}
