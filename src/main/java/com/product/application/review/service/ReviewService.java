package com.product.application.review.service;

import com.product.application.camping.entity.Camping;
import com.product.application.camping.repository.CampingLikeRepository;
import com.product.application.camping.repository.CampingRepository;
import com.product.application.common.ResponseMessage;
import com.product.application.common.exception.CustomException;
import com.product.application.common.exception.ErrorCode;
import com.product.application.review.dto.RequestReviewWriteDto;
import com.product.application.review.entity.Review;
import com.product.application.review.mapper.ReviewMapper;
import com.product.application.review.repository.ReviewLikeRepository;
import com.product.application.review.repository.ReviewRepository;
import com.product.application.s3.entity.Img;
import com.product.application.s3.repository.ImgRepository;
import com.product.application.s3.service.S3UploadService;
import com.product.application.security.jwt.JwtUtil;
import com.product.application.user.entity.Users;
import com.product.application.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final CampingRepository campingRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final CampingLikeRepository campingLikeRepository;
    private final ReviewMapper reviewMapper;
    private final JwtUtil jwtUtil;
    private final S3UploadService s3UploadService;
    private  final ImgRepository imgRepository;

    @Transactional
    public ResponseMessage writeReview(Long campingId, RequestReviewWriteDto requestReviewWriteDto, Users users, List<String> reviewUrl) {
        Camping camping = campingRepository.findById(campingId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
        if (reviewUrl == null || reviewUrl.isEmpty()) { //.isEmpty()도 되는지 확인해보기
            throw new CustomException(ErrorCode.WRONG_INPUT_IMAGE);
        }
        Review review = reviewMapper.requestReviewWriteDtoToEntity(users, camping, requestReviewWriteDto);
        Review reviewTemp = reviewRepository.save(review);
        camping.updateReviewCount(true);

        List<String> imgList = new ArrayList<>();
        for (String imgUrl : reviewUrl) {
            Img img = new Img(imgUrl, review);
            imgRepository.save(img);
            imgList.add(img.getImgUrl());
        }
        System.out.println("review.getId() = " + review.getId());
        return new ResponseMessage<>("Success", 200, review.getId());
    }

    @Transactional
    public ResponseMessage updateReview(Long reviewId, RequestReviewWriteDto requestReviewWriteDto, Long usersId , List<String> reviewUrl) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        if(!review.getUsers().getId().equals(usersId)){
            throw new CustomException(ErrorCode.AUTHORIZATION_UPDATE_FAIL);
        }
        review.update(requestReviewWriteDto);
        reviewRepository.save(review);

        List<Img> imgList = imgRepository.findByReviewId(reviewId);
        for(Img img : imgList){
            imgRepository.delete(img);
            String result = img.getImgUrl().substring(img.getImgUrl().lastIndexOf("/image")+1);
            s3UploadService.deleteImg(result);
        }

        List<String> newImgList = new ArrayList<>();
        for (String imgUrl : reviewUrl) {
            Img img = new Img(imgUrl, review);
            imgRepository.save(img);
            newImgList.add(img.getImgUrl());
        }
        return new ResponseMessage<>("Success", 200, null);
    }


    @Transactional
    public ResponseMessage deleteReview(Long reviewId, Long usersId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        Camping camping = review.getCamping();
        if(!review.getUsers().getId().equals(usersId)){
            throw new CustomException(ErrorCode.AUTHORIZATION_DELETE_FAIL);
        }
        reviewLikeRepository.deleteAllByReviewId(reviewId);

        List<Img> imgList = imgRepository.findByReviewId(reviewId);
        for(Img img : imgList){
            imgRepository.delete(img);
            String result = img.getImgUrl().substring(img.getImgUrl().lastIndexOf("/image")+1);
            s3UploadService.deleteImg(result);
            System.out.println("img.getImgUrl() = " + result);
        }

        reviewRepository.delete(review);
        camping.updateReviewCount(false);
        return new ResponseMessage("Success",200, null);
    }

}
