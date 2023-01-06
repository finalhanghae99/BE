package com.product.application.review.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.review.dto.RequestReviewWriteDto;
import com.product.application.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private ReviewService reviewService;
    @PostMapping("/{campingId}")
    public ResponseEntity writeReview(@PathVariable Long campingId, @RequestBody RequestReviewWriteDto requestReviewWriteDto, HttpServletRequest httpServletRequest){
        reviewService.writeReview(campingId, requestReviewWriteDto, httpServletRequest);
        ResponseMessage responseMessage = new ResponseMessage<>("Success", 200, null);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }
}
