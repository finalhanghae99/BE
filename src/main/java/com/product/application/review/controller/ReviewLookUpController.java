package com.product.application.review.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.review.dto.ResponseReviewAllDto;
import com.product.application.review.dto.ResponseReviewOneDto;
import com.product.application.review.dto.ResponseReviewSixListDto;
import com.product.application.review.service.ReviewLookUpService;
import com.product.application.user.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ReviewLookUpController {
    private final ReviewLookUpService reviewLookUpService;

    //리뷰 전체 조회 -> 특정 캠핑장에 대한 모든 리뷰 정보를 가지고 온다.
    @CrossOrigin(originPatterns = "http://localhost:3000",exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @GetMapping("/review/reviewall/{campingId}")
    public ResponseMessage<?> searchAll(@PathVariable Long campingId, HttpServletRequest request){
        ResponseReviewAllDto responseReviewAllDto = reviewLookUpService.searchAll(campingId, request);
        return new ResponseMessage<>("Success", 200, responseReviewAllDto);
    }
    //특정 리뷰 1개 조회
    @CrossOrigin(originPatterns = "http://localhost:3000",exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @GetMapping("/review/reviewone/{reviewId}")
    public ResponseMessage<?> searchOne(@PathVariable Long reviewId, HttpServletRequest request){
        ResponseReviewOneDto responseReviewOneDto = reviewLookUpService.searchOne(reviewId, request);
        return new ResponseMessage<>("Success", 200, responseReviewOneDto);
    }
    //리뷰 글 조회(특정 캠핑장 리뷰 중 5개 보여주기)
    @CrossOrigin(originPatterns = "http://localhost:3000",exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @GetMapping("/review/listfive/{campingId}")
    public ResponseMessage<?> searchfive(@PathVariable Long campingId, HttpServletRequest request){
        ResponseReviewAllDto responseReviewAllDto = reviewLookUpService.searchfive(campingId, request);
        return new ResponseMessage<>("Success", 200, responseReviewAllDto);
    }

    //리뷰 글 조회(전체 후기 글 조회 - 좋아요 순)
    @CrossOrigin(originPatterns = "http://localhost:3000",exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @GetMapping("/review/likerank")
    public ResponseMessage<?> searchLikeAll(HttpServletRequest request){
        ResponseReviewAllDto responseReviewAllDto = reviewLookUpService.searchLikeAll(request);
        return new ResponseMessage<>("Success", 200, responseReviewAllDto);
    }

    //리뷰 글 조회(전체 후기 글 중 좋아요 순 Top 6반환
    @CrossOrigin(originPatterns = "http://localhost:3000",exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @GetMapping("/review/bestsix")
    public ResponseMessage<?> searchLikeSix(){
        ResponseReviewSixListDto responseReviewSixListDto = reviewLookUpService.searchLikeSix();
        return new ResponseMessage<>("Success", 200, responseReviewSixListDto);
    }
}
