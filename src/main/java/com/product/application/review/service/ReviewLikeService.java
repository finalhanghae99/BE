package com.product.application.review.service;

import com.product.application.common.exception.CustomException;
import com.product.application.review.dto.ReviewLikeResponseDto;
import com.product.application.review.entity.Review;
import com.product.application.review.entity.ReviewLike;
import com.product.application.review.repository.ReviewLikeRepository;
import com.product.application.review.repository.ReviewRepository;
import com.product.application.security.jwt.JwtUtil;
import com.product.application.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.product.application.common.exception.ErrorCode.REVIEW_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ReviewLikeService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ReviewLikeResponseDto updateLike(Long reviewId, Long usersId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new CustomException(REVIEW_NOT_FOUND)
        );

        ReviewLikeResponseDto likeResponseDto = new ReviewLikeResponseDto();
        ReviewLike reviewLike = reviewLikeRepository.findByReviewIdAndUserId(reviewId, usersId).orElse(null);

        if(reviewLike == null){
            reviewLike = new ReviewLike(usersId, review, true);
            reviewLikeRepository.save(reviewLike);
            review.updateLikeCount(true);
            likeResponseDto.setLikeState(true);
            likeResponseDto.setLikeCount(review.getLikeCount());
            return likeResponseDto;
        }
        if(reviewLike.getLikeState()){
            reviewLike.update(false);
            reviewLikeRepository.save(reviewLike);
            review.updateLikeCount(false);
            likeResponseDto.setLikeState(false);
            likeResponseDto.setLikeCount(review.getLikeCount());
            return likeResponseDto;
        }
        reviewLike.update(true);
        reviewLikeRepository.save(reviewLike);
        review.updateLikeCount(true);
        likeResponseDto.setLikeState(true);
        likeResponseDto.setLikeCount(review.getLikeCount());
        return likeResponseDto;
    }
}
