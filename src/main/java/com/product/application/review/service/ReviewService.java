package com.product.application.review.service;

import com.product.application.camping.entity.Camping;
import com.product.application.camping.repository.CampingRepository;
import com.product.application.common.ResponseMessage;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final CampingRepository campingRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final JwtUtil jwtUtil;
    @Transactional
    public ResponseMessage writeReview(Long campingId, RequestReviewWriteDto requestReviewWriteDto, HttpServletRequest request) {
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
                throw new CustomException(ErrorCode.TOKEN_ERROR);
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            users = userRepository.findByUseremail(claims.getSubject()).orElseThrow(
                    () -> new CustomException(ErrorCode.USER_NOT_FOUND)
            );
        }
        Review review = reviewMapper.requestReviewWriteDtoToEntity(users, camping, requestReviewWriteDto);
        reviewRepository.save(review);
        return new ResponseMessage<>("Success", 200, null);
    }

    @Transactional
    public ResponseMessage updateReview(Long reviewId, RequestReviewWriteDto requestReviewWriteDto, HttpServletRequest httpServletRequest) {
        /*
        업데이트 로직
            1. 토큰에서 유저이메일 확인 -> 토큰에서 사용자 정보 추출
            2. 토큰의 사용자 정보와 reviewId가 가지고 있는 유저이메일 비교
            3. 일치하지 않으면 에러 전송
            4. 일치하면 dto의 값으로 엔티티 값 변경
            5. reviewRepository에 업데이트
         */
        // 1.토큰에서 유저이메일 확인
        String token = jwtUtil.resolveToken(httpServletRequest);
        Claims claims;
        // optional로 선언된 usersFromToken을 먼저 null로 선언하고
        Optional<Users> usersFromToken = null;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new CustomException(ErrorCode.TOKEN_ERROR);
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            usersFromToken = userRepository.findByUseremail(claims.getSubject());
        }
        if(!usersFromToken.isPresent()){ // usersFromToken이 null이면 업데이트 권한이 없음을 표시
            throw new CustomException(ErrorCode.AUTHORIZATION_UPDATE_FAIL);
        }
        // review 불러오기
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        // 2.reviewId와 연관된 유저이메일불러와서 토큰안에 있는 유저이메일과 비교하기
        String useremailFromReviewId = review.getUsers().getUseremail();
        // 3.reviewId로 불러온 유저이메일과 토큰안에 있는 유저이메일이 다르면 에러 발생
        if(!useremailFromReviewId.equals(usersFromToken)){
            throw new CustomException(ErrorCode.AUTHORIZATION_UPDATE_FAIL);
        }
        // 4.update requestReviewWriteDto
        review.update(requestReviewWriteDto);
        // 5.entity 저장
        reviewRepository.save(review);
        return new ResponseMessage<>("Success", 200, null);
    }
}
