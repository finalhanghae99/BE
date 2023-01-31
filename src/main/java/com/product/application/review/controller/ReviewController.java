package com.product.application.review.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.review.dto.RequestReviewWriteDto;
import com.product.application.review.service.ReviewService;
import com.product.application.s3.service.S3UploadService;
import com.product.application.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final S3UploadService s3UploadService;

    @PostMapping("/{campingId}")
    public ResponseEntity writeReview(@PathVariable Long campingId,
                                      @RequestPart(value = "requestReviewWriteDto") RequestReviewWriteDto requestReviewWriteDto,
                                      @RequestPart List<MultipartFile> multipartFile,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        List<String> reviewUrl = s3UploadService.upload(multipartFile);
        ResponseMessage responseMessage = reviewService.writeReview(campingId, requestReviewWriteDto, userDetails.getUser(), reviewUrl);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity updateReview(@PathVariable Long reviewId,
                                       @RequestPart(value = "requestReviewWriteDto") RequestReviewWriteDto requestReviewWriteDto,
                                       @RequestPart List<MultipartFile> multipartFile,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long usersId = userDetails.getUserId();
        List<String> reviewUrl = s3UploadService.upload(multipartFile);
        ResponseMessage responseMessage = reviewService.updateReview(reviewId, requestReviewWriteDto,usersId, reviewUrl);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseMessage deleteReview(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long usersId = userDetails.getUserId();
        return reviewService.deleteReview(reviewId, usersId);
    }
}
