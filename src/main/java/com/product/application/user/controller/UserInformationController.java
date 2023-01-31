package com.product.application.user.controller;


import com.product.application.common.ResponseMessage;
import com.product.application.review.dto.ResponseReviewOneListDto;
import com.product.application.security.UserDetailsImpl;
import com.product.application.user.dto.RequestUserInfoDto;
import com.product.application.user.dto.ResponseUserCampingInfoDto;
import com.product.application.user.dto.ResponseUserInfoDto;
import com.product.application.user.dto.ResponseUserReservationDto;

import com.product.application.user.service.UserInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserInformationController {
    private final UserInformationService userInformationService;


    @GetMapping("/mypage")
    public ResponseMessage<?> userInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        ResponseUserInfoDto responseUserInfoDto = userInformationService.userInfo(userDetails.getUser());
        return new ResponseMessage<>("Success", 200, responseUserInfoDto);
    }

    @GetMapping("/mypage/camping")
    public ResponseMessage<?> userCampingInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Long usersId = userDetails.getUserId();
        ResponseUserCampingInfoDto responseUserCampingInfo = userInformationService.userCampingInfo(usersId);
        return new ResponseMessage<>("Success", 200, responseUserCampingInfo);
    }

    @GetMapping("/mypage/update")
    public ResponseMessage<?> userInfoChange(@RequestBody RequestUserInfoDto requestUserInfoDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        ResponseUserInfoDto responseUserInfoDto = userInformationService.userInfoChange(requestUserInfoDto, userDetails.getUser());
        return new ResponseMessage<>("Success", 200, responseUserInfoDto);
    }

    @GetMapping("/mypage/review")
    public ResponseMessage<?> userReviewInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Long usersId = userDetails.getUserId();
        ResponseReviewOneListDto responseReviewOneListDto = userInformationService.userReviewInfo(usersId);
        return new ResponseMessage<>("Success", 200, responseReviewOneListDto);
    }

    @GetMapping("/mypage/reservation")
    public ResponseMessage<?> userReservationInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Long usersId = userDetails.getUserId();
        ResponseUserReservationDto responseUserReservationDto = userInformationService.userReservationInfo(usersId);
        return new ResponseMessage<>("Success", 200, responseUserReservationDto);
    }
}
