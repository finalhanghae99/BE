package com.product.application.review.service;

import com.product.application.camping.entity.Camping;
import com.product.application.camping.repository.CampingRepository;
import com.product.application.common.exception.CustomException;
import com.product.application.common.exception.ErrorCode;
import com.product.application.review.dto.RequestReviewWriteDto;
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

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final CampingRepository campingRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final JwtUtil jwtUtil;
    @Transactional
    public void writeReview(Long campingId, RequestReviewWriteDto requestReviewWriteDto, HttpServletRequest request) {
        Camping camping = campingRepository.findById(campingId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        Users users = null;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            users = userRepository.findByUseremail(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
        }
        Review review = reviewMapper.requestReviewWriteDtoToEntity(users, camping, requestReviewWriteDto);
        reviewRepository.save(review);
    }
}
