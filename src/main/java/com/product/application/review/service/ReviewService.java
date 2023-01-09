package com.product.application.review.service;

import com.product.application.camping.entity.Camping;
import com.product.application.camping.entity.CampingLike;
import com.product.application.camping.repository.CampingLikeRepository;
import com.product.application.camping.repository.CampingRepository;
import com.product.application.common.ResponseMessage;
import com.product.application.common.exception.CustomException;
import com.product.application.common.exception.ErrorCode;
import com.product.application.review.dto.RequestReviewWriteDto;
import com.product.application.review.dto.ResponseFindListTenDto;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final CampingRepository campingRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final CampingLikeRepository campingLikeRepository;
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
        String token = jwtUtil.resolveToken(httpServletRequest);
        if (token != null) {
            Claims claims;
            Users usersFromToken;
            // 토큰이 유효하다면 사용자 정보 가져오기
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new CustomException(ErrorCode.TOKEN_ERROR);
            }
            // 토큰에서 가져온 useremail을 사용하여 DB 조회
            usersFromToken = userRepository.findByUseremail(claims.getSubject()).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
            // @pathvariable reviewId로 DB에서 review 조회
            Review review = reviewRepository.findById(reviewId).orElseThrow(()->new CustomException(ErrorCode.REVIEW_NOT_FOUND));
            // 토큰안에 있는 유저이메일, 리뷰와 연결된 유저이메일 비교
            String useremailFromReview = review.getUsers().getUseremail();
            String useremailFromToken = usersFromToken.getUseremail();
            if(!useremailFromReview.equals(useremailFromToken)){
                throw new CustomException(ErrorCode.AUTHORIZATION_UPDATE_FAIL);
            }
            // requestReviewWriteDto로 review 엔티티 업데이트
            review.update(requestReviewWriteDto);
            // DB에 entity 저장
            reviewRepository.save(review);
            return new ResponseMessage<>("Success", 200, null);
        } else {
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        }
    }

    @Transactional
    public ResponseMessage findListTen(List<Long> list, HttpServletRequest request) {
        // ResponseFindListTenDto는
        // campingId, imageUrl, campingName,
        // address1, address2, address3;
        // "reviewCount", "campingLikeState";
        // campingEnv, campingType, campingFac, campingSurroundFac를 가지고 있음
        // 이 중에서,
        // reviewCount는 review의 갯수 :: reviewRepository에서 countByCamping(camping)으로 찾음
        // campingLikeState는 findByCampingAndUsersId(camping,usersFromToken.get().getId())로 찾음.
        // 이 때, Optional<CampingLike>를 반환 --> null이면 campingLikeState가 false / null이 아니면 campingLikeState가 true
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new CustomException(ErrorCode.TOKEN_ERROR);
            }
            Camping tempCamping;
            Long reviewCount;
            boolean campingLikeState;
            Users usersFromToken = userRepository.findByUseremail(claims.getSubject()).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
            List<ResponseFindListTenDto> dtoList = new ArrayList<>();
            for(Long campingId : list){
                tempCamping = campingRepository.findById(campingId).orElseThrow(()->new CustomException(ErrorCode.CAMPING_NOT_FOUND));
                reviewCount = reviewRepository.countByCamping(tempCamping);
                Optional<CampingLike> optionalCampingLike = campingLikeRepository.findByCampingAndUsersId(tempCamping,usersFromToken.getId());
                campingLikeState = optionalCampingLike.isPresent();
                dtoList.add(new ResponseFindListTenDto(tempCamping,reviewCount,campingLikeState));
            }
            return new ResponseMessage("Success",200, dtoList);
        } else { // 토큰이 존재하지 않으면 에러 발생
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        }
    }
}
