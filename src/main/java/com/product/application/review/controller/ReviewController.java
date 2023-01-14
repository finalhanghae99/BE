package com.product.application.review.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.review.dto.RequestFindListTenDto;
import com.product.application.review.dto.RequestReviewWriteDto;
import com.product.application.review.service.ReviewService;
import com.product.application.s3.Img;
import com.product.application.s3.S3Config;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    private final S3Config s3Config;
    @PostMapping("/{campingId}")
    public ResponseEntity writeReview(@PathVariable Long campingId,
                                      @RequestPart(value = "requestReviewWriteDto") RequestReviewWriteDto requestReviewWriteDto,
                                      @RequestPart List<MultipartFile> multipartFile,
                                      HttpServletRequest httpServletRequest){
        List<String> reviewUrl = s3Config.upload(multipartFile);
        ResponseMessage responseMessage = reviewService.writeReview(campingId, requestReviewWriteDto, httpServletRequest, reviewUrl);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity updateReview(@PathVariable Long reviewId,
                                       @RequestPart(value = "requestReviewWriteDto") RequestReviewWriteDto requestReviewWriteDto,
                                       @RequestPart List<MultipartFile> multipartFile,
                                       HttpServletRequest httpServletRequest){
        List<String> reviewUrl = s3Config.upload(multipartFile);
        ResponseMessage responseMessage = reviewService.updateReview(reviewId, requestReviewWriteDto, httpServletRequest, reviewUrl);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

    @PostMapping("/listten")
    public ResponseMessage findListTen(@RequestBody RequestFindListTenDto requestFindListTenDto, HttpServletRequest request){
        List<Long> list = requestFindListTenDto.getCampingIdList();
        return reviewService.findListTen(list,request);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseMessage deleteReview(@PathVariable Long reviewId, HttpServletRequest request){
        return reviewService.deleteReview(reviewId, request);
    }
}
