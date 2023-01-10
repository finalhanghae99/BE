package com.product.application.review.service;

import com.product.application.camping.entity.Camping;
import com.product.application.camping.repository.CampingRepository;
import com.product.application.common.exception.CustomException;
import com.product.application.review.dto.ResponseReviewAllDto;
import com.product.application.review.dto.ResponseReviewListDto;
import com.product.application.review.dto.ResponseReviewOneDto;
import com.product.application.review.entity.Review;
import com.product.application.review.mapper.ReviewMapper;
import com.product.application.review.repository.ReviewRepository;
import com.product.application.user.entity.Users;
import com.product.application.user.jwt.JwtUtil;
import com.product.application.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static com.product.application.common.exception.ErrorCode.*;
import static com.product.application.common.exception.ErrorCode.TOKEN_ERROR;

@Service
@RequiredArgsConstructor
public class ReviewLookUpService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final CampingRepository campingRepository;
    private final ReviewMapper reviewMapper;

    @Transactional
    public ResponseReviewAllDto searchAll(Long campingId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new CustomException(TOKEN_ERROR);
            }

            Users users = userRepository.findByUseremail(claims.getSubject()).orElseThrow(
                    () -> new CustomException(USER_NOT_FOUND)
            );
            Long usersId = users.getId();

            Camping camping = campingRepository.findById(campingId).orElseThrow(
                    () -> new CustomException(CAMPING_NOT_FOUND)
            );
            List<ResponseReviewListDto> reviewListDtos = new ArrayList<>();
            List<Review> reviewList = camping.getReviewList();

            for(Review review : reviewList){
                reviewListDtos.add(reviewMapper.toResponseReviewListDto(review, usersId));
            }

            ResponseReviewAllDto responseReviewAllDto = new ResponseReviewAllDto(reviewListDtos);
            return responseReviewAllDto;

        } else {
            throw new CustomException(TOKEN_ERROR);
        }
    }

    public ResponseReviewOneDto searchOne(Long reviewId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new CustomException(TOKEN_ERROR);
            }

            Users users = userRepository.findByUseremail(claims.getSubject()).orElseThrow(
                    () -> new CustomException(USER_NOT_FOUND)
            );
            Long usersId = users.getId();
            Review review = reviewRepository.findById(reviewId).orElseThrow(
                    () -> new CustomException(REVIEW_NOT_FOUND)
            );

            return reviewMapper.toResponseReviewOne(review, usersId);

        } else {
            throw new CustomException(TOKEN_ERROR);
        }
    }

    public ResponseReviewAllDto searchfive(Long campingId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new CustomException(TOKEN_ERROR);
            }

            Users users = userRepository.findByUseremail(claims.getSubject()).orElseThrow(
                    () -> new CustomException(USER_NOT_FOUND)
            );
            Long usersId = users.getId();

            Camping camping = campingRepository.findById(campingId).orElseThrow(
                    () -> new CustomException(CAMPING_NOT_FOUND)
            );

            List<Review> reviewList = camping.getReviewList();
            if(reviewList.size() < 6){
                List<ResponseReviewListDto> reviewListDtos = new ArrayList<>();
                for(Review review : reviewList) {
                    reviewListDtos.add(reviewMapper.toResponseReviewListDto(review, usersId));
                }
                ResponseReviewAllDto responseReviewAllDto = new ResponseReviewAllDto(reviewListDtos);
                return responseReviewAllDto;
            }
            List<Review> reviewListTop5 = reviewRepository.findTop5ByCampingIdOrderByModifiedAtDesc(campingId);
            List<ResponseReviewListDto> reviewListDtos = new ArrayList<>();
            for(Review review : reviewListTop5) {
                reviewListDtos.add(reviewMapper.toResponseReviewListDto(review, usersId));
            }
            ResponseReviewAllDto responseReviewAllDto = new ResponseReviewAllDto(reviewListDtos);
            return responseReviewAllDto;

        } else {
            throw new CustomException(TOKEN_ERROR);
        }
    }
}
