package com.product.application.user.controller;


import com.product.application.common.ResponseMessage;
import com.product.application.review.dto.ResponseReviewOneListDto;
import com.product.application.s3.entity.Img;
import com.product.application.s3.service.S3UploadService;
import com.product.application.security.UserDetailsImpl;
import com.product.application.user.dto.*;

import com.product.application.user.entity.Users;
import com.product.application.user.service.UserInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserInformationController {
    private final UserInformationService userInformationService;
    private final S3UploadService s3UploadService;


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

    @PutMapping("/mypage/update")
    public ResponseMessage<?> userInfoChange(@RequestPart RequestUserInfoDto requestUserInfoDto,
                                             @RequestPart List<MultipartFile> profileImageUrl,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<String> imgUrl = s3UploadService.upload(profileImageUrl);
        ResponseUserInfoDto responseUserInfoDto = userInformationService.userInfoChange(requestUserInfoDto, imgUrl, userDetails.getUser());
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
