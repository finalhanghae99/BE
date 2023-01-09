package com.product.application.review.service;

import com.product.application.common.exception.CustomException;
import com.product.application.review.dto.ReviewLikeResponseDto;
import com.product.application.review.entity.Review;
import com.product.application.review.entity.ReviewLike;
import com.product.application.review.repository.ReviewLikeRepository;
import com.product.application.review.repository.ReviewRepository;
import com.product.application.user.entity.Users;
import com.product.application.user.jwt.JwtUtil;
import com.product.application.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import static com.product.application.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReviewLikeService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ReviewLikeResponseDto updateLike(Long reviewId, HttpServletRequest request) {
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

            ReviewLikeResponseDto likeResponseDto = new ReviewLikeResponseDto();

            ReviewLike reviewLike = reviewLikeRepository.findByReviewIdAndUserId(reviewId, usersId).orElse(null);

            if(reviewLike == null){
                reviewLike = new ReviewLike(usersId, review);
                reviewLikeRepository.save(reviewLike);
                review.updateLikeCount(true);
                likeResponseDto.setLikeState(true);
                likeResponseDto.setLikeCount(review.getLikeCount());
                return likeResponseDto;
            }

            reviewLikeRepository.delete(reviewLike);
            review.updateLikeCount(false);
            likeResponseDto.setLikeState(false);
            likeResponseDto.setLikeCount(review.getLikeCount());
            return likeResponseDto;
        } else {
            throw new CustomException(TOKEN_ERROR);
        }
    }
}
