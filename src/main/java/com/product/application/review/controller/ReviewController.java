package com.product.application.review.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.review.dto.RequestReviewWriteDto;
import com.product.application.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping("/{campingId}")
    public ResponseEntity writeReview(@PathVariable Long campingId, @RequestBody RequestReviewWriteDto requestReviewWriteDto, HttpServletRequest httpServletRequest){
        ResponseMessage responseMessage = reviewService.writeReview(campingId, requestReviewWriteDto, httpServletRequest);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }
    @PutMapping("/{reviewId}")
    public ResponseEntity updateReview(@PathVariable Long reviewId, @RequestBody RequestReviewWriteDto requestReviewWriteDto, HttpServletRequest httpServletRequest){
        ResponseMessage responseMessage = reviewService.updateReview(reviewId, requestReviewWriteDto, httpServletRequest);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }
}
