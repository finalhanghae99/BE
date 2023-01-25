package com.product.application.review.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.review.dto.RequestFindListTenDto;
import com.product.application.review.dto.RequestReviewWriteDto;
import com.product.application.review.service.ReviewService;
import com.product.application.s3.service.S3UploadService;
import com.product.application.user.jwt.JwtUtil;
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

    private final S3UploadService s3UploadService;
    @CrossOrigin(origins = {"http://campingzipbeta.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:3000"}, exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @PostMapping("/{campingId}")
    public ResponseEntity writeReview(@PathVariable Long campingId,
                                      @RequestPart(value = "requestReviewWriteDto") RequestReviewWriteDto requestReviewWriteDto,
                                      @RequestPart List<MultipartFile> multipartFile,
                                      HttpServletRequest httpServletRequest){
        List<String> reviewUrl = s3UploadService.upload(multipartFile);
        ResponseMessage responseMessage = reviewService.writeReview(campingId, requestReviewWriteDto, httpServletRequest, reviewUrl);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }
    @CrossOrigin(origins = {"http://campingzipbeta.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:3000"}, exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @PutMapping("/{reviewId}")
    public ResponseEntity updateReview(@PathVariable Long reviewId,
                                       @RequestPart(value = "requestReviewWriteDto") RequestReviewWriteDto requestReviewWriteDto,
                                       @RequestPart List<MultipartFile> multipartFile,
                                       HttpServletRequest httpServletRequest){
        List<String> reviewUrl = s3UploadService.upload(multipartFile);
        ResponseMessage responseMessage = reviewService.updateReview(reviewId, requestReviewWriteDto, httpServletRequest, reviewUrl);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }
    @CrossOrigin(origins = {"http://campingzipbeta.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:3000"}, exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @PostMapping("/listten")
    public ResponseMessage findListTen(@RequestBody RequestFindListTenDto requestFindListTenDto, HttpServletRequest request){
        List<Long> list = requestFindListTenDto.getCampingIdList();
        return reviewService.findListTen(list,request);
    }
    @CrossOrigin(origins = {"http://campingzipbeta.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:3000"}, exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @DeleteMapping("/{reviewId}")
    public ResponseMessage deleteReview(@PathVariable Long reviewId, HttpServletRequest request){
        return reviewService.deleteReview(reviewId, request);
    }
}
