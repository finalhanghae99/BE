package com.product.application.review.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.review.dto.ReviewLikeResponseDto;
import com.product.application.review.service.ReviewLikeService;
import com.product.application.user.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ReviewLikeController {
    private final ReviewLikeService reviewLikeService;

    @CrossOrigin(origins = {"http://campingzipbeta.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:3000"}, exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @PostMapping("/review/{reviewId}/like")
    public ResponseMessage<?> updateLike(@PathVariable Long reviewId, HttpServletRequest request){
        ReviewLikeResponseDto likeResponseDto = reviewLikeService.updateLike(reviewId, request);
        return new ResponseMessage<>("Success", 200, likeResponseDto);
    }
}
