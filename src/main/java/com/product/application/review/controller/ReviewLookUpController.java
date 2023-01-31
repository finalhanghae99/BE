package com.product.application.review.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.review.dto.ResponseReviewAllDto;
import com.product.application.review.dto.ResponseReviewOneDto;
import com.product.application.review.dto.ResponseReviewSixListDto;
import com.product.application.review.service.ReviewLookUpService;
import com.product.application.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewLookUpController {
    private final ReviewLookUpService reviewLookUpService;

    //리뷰 전체 조회 -> 특정 캠핑장에 대한 모든 리뷰 정보를 가지고 온다.
    @GetMapping("/reviewlookup/reviewall/{campingId}")
    public ResponseMessage<?> searchAll(@PathVariable Long campingId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long usersId;
        if(userDetails != null){
            usersId = userDetails.getUserId();
        } else {
            usersId = 0L;
        }

        ResponseReviewAllDto responseReviewAllDto = reviewLookUpService.searchAll(campingId, usersId);
        return new ResponseMessage<>("Success", 200, responseReviewAllDto);
    }

    //특정 리뷰 1개 조회
    @GetMapping("/reviewlookup/reviewone/{reviewId}")
    public ResponseMessage<?> searchOne(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long usersId;
        if(userDetails != null){
            usersId = userDetails.getUserId();
        } else {
            usersId = 0L;
        }

        ResponseReviewOneDto responseReviewOneDto = reviewLookUpService.searchOne(reviewId, usersId);
        return new ResponseMessage<>("Success", 200, responseReviewOneDto);
    }

    //리뷰 글 조회(특정 캠핑장 리뷰 중 5개 보여주기)
    @GetMapping("/reviewlookup/listfive/{campingId}")
    public ResponseMessage<?> searchfive(@PathVariable Long campingId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long usersId;
        if(userDetails != null){
            usersId = userDetails.getUserId();
        } else {
            usersId = 0L;
        }

        ResponseReviewAllDto responseReviewAllDto = reviewLookUpService.searchfive(campingId, usersId);
        return new ResponseMessage<>("Success", 200, responseReviewAllDto);
    }

    //리뷰 글 조회(전체 후기 글 조회 - 좋아요 순)

    @GetMapping("/reviewlookup/likerank")
    public ResponseMessage<?> searchLikeAll(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Long usersId;
        if(userDetails != null){
            usersId = userDetails.getUserId();
        } else {
            usersId = 0L;
        }
        ResponseReviewAllDto responseReviewAllDto = reviewLookUpService.searchLikeAll(usersId);
        return new ResponseMessage<>("Success", 200, responseReviewAllDto);
    }

    //리뷰 글 조회(전체 후기 글 중 좋아요 순 Top 6반환

    @GetMapping("/reviewlookup/bestsix")
    public ResponseMessage<?> searchLikeSix(){
        ResponseReviewSixListDto responseReviewSixListDto = reviewLookUpService.searchLikeSix();
        return new ResponseMessage<>("Success", 200, responseReviewSixListDto);
    }

}
