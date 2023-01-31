package com.product.application.review.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.review.dto.ReviewLikeResponseDto;
import com.product.application.review.service.ReviewLikeService;
import com.product.application.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewLikeController {
    private final ReviewLikeService reviewLikeService;

    @PostMapping("/review/{reviewId}/like")
    public ResponseMessage<?> updateLike(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long usersId = userDetails.getUserId();
        ReviewLikeResponseDto likeResponseDto = reviewLikeService.updateLike(reviewId, usersId);
        return new ResponseMessage<>("Success", 200, likeResponseDto);
    }
}
