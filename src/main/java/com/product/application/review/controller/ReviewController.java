package com.product.application.review.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.review.dto.RequestFindListTenDto;
import com.product.application.review.dto.RequestReviewWriteDto;
import com.product.application.review.service.ReviewService;
import com.product.application.s3.S3Config;
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

    private final S3Config s3Config;
    @CrossOrigin(originPatterns = "http://localhost:3000",exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @PostMapping("/{campingId}")
    public ResponseEntity writeReview(@PathVariable Long campingId,
                                      @RequestPart(value = "requestReviewWriteDto") RequestReviewWriteDto requestReviewWriteDto,
                                      @RequestPart List<MultipartFile> multipartFile,
                                      HttpServletRequest httpServletRequest){
        List<String> reviewUrl = s3Config.upload(multipartFile);
        ResponseMessage responseMessage = reviewService.writeReview(campingId, requestReviewWriteDto, httpServletRequest, reviewUrl);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }
    @CrossOrigin(originPatterns = "http://localhost:3000",exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @PutMapping("/{reviewId}")
    public ResponseEntity updateReview(@PathVariable Long reviewId,
                                       @RequestPart(value = "requestReviewWriteDto") RequestReviewWriteDto requestReviewWriteDto,
                                       @RequestPart List<MultipartFile> multipartFile,
                                       HttpServletRequest httpServletRequest){
        List<String> reviewUrl = s3Config.upload(multipartFile);
        ResponseMessage responseMessage = reviewService.updateReview(reviewId, requestReviewWriteDto, httpServletRequest, reviewUrl);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }
    @CrossOrigin(originPatterns = "http://localhost:3000",exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @PostMapping("/listten")
    public ResponseMessage findListTen(@RequestBody RequestFindListTenDto requestFindListTenDto, HttpServletRequest request){
        List<Long> list = requestFindListTenDto.getCampingIdList();
        return reviewService.findListTen(list,request);
    }
    @CrossOrigin(originPatterns = "http://localhost:3000",exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @DeleteMapping("/{reviewId}")
    public ResponseMessage deleteReview(@PathVariable Long reviewId, HttpServletRequest request){
        return reviewService.deleteReview(reviewId, request);
    }
}
