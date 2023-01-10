package com.product.application.review.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.review.dto.ResponseReviewAllDto;
import com.product.application.review.dto.ResponseReviewListDto;
import com.product.application.review.service.ReviewLookUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ReviewLookUpController {
    private final ReviewLookUpService reviewLookUpService;

    //리뷰 전체 조회 -> 특정 캠핑장에 대한 모든 리뷰 정보를 가지고 온다.
    @GetMapping("/review/{campingId}")
    public ResponseMessage<?> searchAll(@PathVariable Long campingId, HttpServletRequest request){
        ResponseReviewAllDto responseReviewAllDto = reviewLookUpService.searchAll(campingId, request);
        return new ResponseMessage<>("Success", 200, responseReviewAllDto);
    }
}
