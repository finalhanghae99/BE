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
import com.product.application.review.entity.ReviewLike;
import com.product.application.review.mapper.ReviewMapper;
import com.product.application.review.repository.ReviewLikeRepository;
import com.product.application.review.repository.ReviewRepository;
import com.product.application.s3.entity.Img;
import com.product.application.s3.repository.ImgRepository;
import com.product.application.s3.service.S3UploadService;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public ResponseMessage writeReview(Long campingId, RequestReviewWriteDto requestReviewWriteDto, HttpServletRequest request, List<String> reviewUrl) {
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
        Camping camping = campingRepository.findById(campingId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
        if (reviewUrl == null || reviewUrl.isEmpty()) { //.isEmpty()도 되는지 확인해보기
            throw new CustomException(ErrorCode.WRONG_INPUT_IMAGE);
        }

            Review review = reviewMapper.requestReviewWriteDtoToEntity(users, camping, requestReviewWriteDto);
            reviewRepository.save(review);

            List<String> imgList = new ArrayList<>();
            for (String imgUrl : reviewUrl) {
                Img img = new Img(imgUrl, review);
                imgRepository.save(img);
                imgList.add(img.getImgUrl());
            }
            return new ResponseMessage<>("Success", 200, null);
        }

    @Transactional
    public ResponseMessage updateReview(Long reviewId, RequestReviewWriteDto requestReviewWriteDto, HttpServletRequest httpServletRequest, List<String> reviewUrl) {
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
        // 추가로
        // campingEnv, campingType, campingFac, campingSurroundFac는 DB에 String으로 저장되어 있지만,
        // 배열로 바꿔서 반환해주기
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
                if(optionalCampingLike.isEmpty()){
                    campingLikeState = false;
                } else {
                    campingLikeState = optionalCampingLike.get().getCampingLikeState();
                }

                // ** campingEnv, campingType, campingFac, campingSurroundFac를 스트링에서 리스트로 변환해서 전달
                // (1) campingEnv
                String campingEnv = tempCamping.getCampingEnv();
                String[] campingEnvArr = campingEnv.split(","); // 단일 단어인 경우에도 배열에 잘 들어감
                List<String> campingEnvList = Stream.of(campingEnvArr).collect(Collectors.toList());
                if(campingEnvList.size()==1 && campingEnvList.get(0) == "") campingEnvList.remove(0);
                // (2) campingEnv
                String campingType = tempCamping.getCampingType();
                String[] campingTypeArr = campingType.split(",");
                List<String> campingTypeList = Stream.of(campingTypeArr).collect(Collectors.toList());
                if(campingTypeList.size() == 1 && campingTypeList.get(0) == "") campingTypeList.remove(0);
                // (3) campingEnv
                String campingFac = tempCamping.getCampingFac();
                String[] campingFacArr = campingFac.split(",");
                List<String> campingFacList = Stream.of(campingFacArr).collect(Collectors.toList());
                if(campingFacList.size() == 1 && campingFacList.get(0) == "") campingFacList.remove(0);
                // (4) campingEnv
                String campingSurroundFac = tempCamping.getCampingSurroundFac();
                String[] campingSurroundFacArr = campingSurroundFac.split(",");
                List<String> campingSurroundFacList = Stream.of(campingSurroundFacArr).collect(Collectors.toList());
                if(campingSurroundFacList.size() == 1 && campingSurroundFacList.get(0) == "") campingSurroundFacList.remove(0);

                dtoList.add(new ResponseFindListTenDto(tempCamping,reviewCount,campingLikeState,campingEnvList,campingTypeList,campingFacList,campingSurroundFacList));
            }
            return new ResponseMessage("Success",200, dtoList);
        } else { // 토큰이 존재하지 않으면 에러 발생
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        }
    }

    @Transactional
    public ResponseMessage deleteReview(Long reviewId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new CustomException(ErrorCode.TOKEN_ERROR);
            }
            Review reviewFromReviewId = reviewRepository.findById(reviewId).orElseThrow(()->new CustomException(ErrorCode.REVIEW_NOT_FOUND));
            String useremailFromReview = reviewFromReviewId.getUsers().getUseremail();
            String useremailFromToken = claims.getSubject();
            if(!useremailFromReview.equals(useremailFromToken)){
                throw new CustomException(ErrorCode.AUTHORIZATION_DELETE_FAIL);
            }
            List<ReviewLike> reviewLikeList = reviewLikeRepository.findByReviewId(reviewFromReviewId.getId());
            for(ReviewLike reviewLike : reviewLikeList) {
                reviewLikeRepository.delete(reviewLike);
            }
            List<Img> imgList = imgRepository.findByReviewId(reviewId);
            for(Img img : imgList){
                imgRepository.delete(img);
                String result = img.getImgUrl().substring(img.getImgUrl().lastIndexOf("/image")+1);
                s3UploadService.deleteImg(result);
                System.out.println("img.getImgUrl() = " + result);
            }
            reviewRepository.delete(reviewFromReviewId);
            return new ResponseMessage("Success",200, null);
        } else { // 토큰이 존재하지 않으면 에러 발생
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        }
    }
}
