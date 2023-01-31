package com.product.application.review.service;

import com.product.application.camping.entity.Camping;
import com.product.application.camping.repository.CampingRepository;
import com.product.application.common.exception.CustomException;
import com.product.application.review.dto.*;
import com.product.application.review.entity.Review;
import com.product.application.review.mapper.ReviewMapper;
import com.product.application.review.repository.ReviewRepository;
import com.product.application.s3.entity.Img;
import com.product.application.s3.repository.ImgRepository;
import com.product.application.security.jwt.JwtUtil;
import com.product.application.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.product.application.common.exception.ErrorCode.CAMPING_NOT_FOUND;
import static com.product.application.common.exception.ErrorCode.REVIEW_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ReviewLookUpService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final CampingRepository campingRepository;
    private final ReviewMapper reviewMapper;
    private final ImgRepository imgRepository;

    @Transactional
    public ResponseReviewAllDto searchAll(Long campingId, Long usersId) {
        Camping camping = campingRepository.findById(campingId).orElseThrow(
                () -> new CustomException(CAMPING_NOT_FOUND)
        );
        List<ResponseReviewListDto> reviewListDtos = new ArrayList<>();
        List<Review> reviewList = camping.getReviewList();

        for(Review review : reviewList){
            List<Img> urlList = imgRepository.findByReviewId(review.getId());
            List<String> imgList = new ArrayList<>();
            for(Img imgUrl : urlList){
                Img img = new Img(imgUrl);
                imgList.add(img.getImgUrl());
            }
            reviewListDtos.add(reviewMapper.toResponseReviewListDto(review, usersId, imgList));
        }
        ResponseReviewAllDto responseReviewAllDto = new ResponseReviewAllDto(reviewListDtos);
        return responseReviewAllDto;
    }

    public ResponseReviewOneDto searchOne(Long reviewId, Long usersId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new CustomException(REVIEW_NOT_FOUND)
        );

        List<Img> urlList = imgRepository.findByReviewId(review.getId());
        List<String> imgList = new ArrayList<>();
        for(Img imgUrl : urlList){
            Img img = new Img(imgUrl);
            imgList.add(img.getImgUrl());
        }

        return reviewMapper.toResponseReviewOne(review, usersId, imgList);
    }

    public ResponseReviewAllDto searchfive(Long campingId, Long usersId) {
        Camping camping = campingRepository.findById(campingId).orElseThrow(
                () -> new CustomException(CAMPING_NOT_FOUND)
        );

        List<Review> reviewList = camping.getReviewList();
        if(reviewList.size() < 6){
            List<ResponseReviewListDto> reviewListDtos = new ArrayList<>();
            for(Review review : reviewList) {
                List<Img> urlList = imgRepository.findByReviewId(review.getId());
                List<String> imgList = new ArrayList<>();
                for(Img imgUrl : urlList){
                    Img img = new Img(imgUrl);
                    imgList.add(img.getImgUrl());
                }
                reviewListDtos.add(reviewMapper.toResponseReviewListDto(review, usersId, imgList));
            }
            ResponseReviewAllDto responseReviewAllDto = new ResponseReviewAllDto(reviewListDtos);
            return responseReviewAllDto;
        }
        List<Review> reviewListTop5 = reviewRepository.findTop5ByCampingIdOrderByModifiedAtDesc(campingId);
        List<ResponseReviewListDto> reviewListDtos = new ArrayList<>();
        for(Review review : reviewListTop5) {
            List<Img> urlList = imgRepository.findByReviewId(review.getId());
            List<String> imgList = new ArrayList<>();
            for(Img imgUrl : urlList){
                Img img = new Img(imgUrl);
                imgList.add(img.getImgUrl());
            }
            reviewListDtos.add(reviewMapper.toResponseReviewListDto(review, usersId, imgList));
        }
        ResponseReviewAllDto responseReviewAllDto = new ResponseReviewAllDto(reviewListDtos);
        return responseReviewAllDto;
    }

    public ResponseReviewAllDto searchLikeAll(Long usersId) {
        List<Review> reviewList = reviewRepository.selectAllSQL();
        List<ResponseReviewListDto> reviewListDtos = new ArrayList<>();
        for(Review review : reviewList) {
            List<Img> urlList = imgRepository.findByReviewId(review.getId());
            List<String> imgList = new ArrayList<>();
            for(Img imgUrl : urlList){
                Img img = new Img(imgUrl);
                imgList.add(img.getImgUrl());
            }
            reviewListDtos.add(reviewMapper.toResponseReviewListDto(review, usersId, imgList));
        }
        ResponseReviewAllDto responseReviewAllDto = new ResponseReviewAllDto(reviewListDtos);
        return responseReviewAllDto;
    }

    public ResponseReviewSixListDto searchLikeSix() {
        List<Review> reviewList = reviewRepository.selectSixSQL();
        List<ResponseReviewSixDto> responseReviewSixDtos = new ArrayList<>();
        for(Review review : reviewList){
            responseReviewSixDtos.add(reviewMapper.toResponseReviewSix(review));
        }
        ResponseReviewSixListDto responseReviewSixListDto = new ResponseReviewSixListDto(responseReviewSixDtos);
        return responseReviewSixListDto;
    }

}
