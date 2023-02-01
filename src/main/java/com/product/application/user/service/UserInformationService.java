package com.product.application.user.service;

import com.product.application.camping.entity.Camping;
import com.product.application.camping.entity.CampingLike;
import com.product.application.camping.repository.CampingLikeRepository;
import com.product.application.common.exception.CustomException;
import com.product.application.reservation.dto.ResponseSearchDto;
import com.product.application.reservation.entity.Reservation;
import com.product.application.reservation.mapper.ReservationMapper;
import com.product.application.reservation.repository.ReservationRepository;
import com.product.application.review.dto.ResponseReviewOneDto;
import com.product.application.review.dto.ResponseReviewOneListDto;
import com.product.application.review.entity.Review;
import com.product.application.review.mapper.ReviewMapper;
import com.product.application.review.repository.ReviewRepository;
import com.product.application.s3.entity.Img;
import com.product.application.s3.repository.ImgRepository;
import com.product.application.security.jwt.JwtUtil;
import com.product.application.user.dto.*;
import com.product.application.user.entity.Users;
import com.product.application.user.mapper.UserMapper;
import com.product.application.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.product.application.common.exception.ErrorCode.DUPLICATE_NICKNAME;

@RequiredArgsConstructor
@Service
public class UserInformationService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final CampingLikeRepository campingLikeRepository;
    private final ReviewRepository reviewRepository;
    private final UserMapper userMapper;
    private final ReviewMapper reviewMapper;
    private final ImgRepository imgRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ResponseUserInfoDto userInfo(Users users) {
        return userMapper.toResponseUserInfo(users);
    }

    public ResponseUserCampingInfoDto userCampingInfo(Long usersId) {
        List<CampingLike> campingLikeList = campingLikeRepository.findAllByusersId(usersId);

        List<Camping> CampingList = new ArrayList<>();
        for(CampingLike campingLike : campingLikeList ){
            if(campingLike.getCampingLikeState()==true){
                CampingList.add(campingLike.getCamping());
            }
        }

        List<ResponseUserCampingInfoListDto> responseUserCampingInfoListDto = new ArrayList<>();
        for(Camping camping : CampingList){
            String campingEnv = camping.getCampingEnv();
            String[] campingEnvArr = campingEnv.split(",");
            List<String> campingEnvList = Stream.of(campingEnvArr).collect(Collectors.toList());
            if(campingEnvList.size()==1 && campingEnvList.get(0).equals("")) campingEnvList.remove(0);

            String campingType = camping.getCampingType();
            String[] campingTypeArr = campingType.split(",");
            List<String> campingTypeList = Stream.of(campingTypeArr).collect(Collectors.toList());
            if(campingTypeList.size() == 1 && campingTypeList.get(0).equals("")) campingTypeList.remove(0);

            String campingFac = camping.getCampingFac();
            String[] campingFacArr = campingFac.split(",");
            List<String> campingFacList = Stream.of(campingFacArr).collect(Collectors.toList());
            if(campingFacList.size() == 1 && campingFacList.get(0).equals("")) campingFacList.remove(0);

            String campingSurroundFac = camping.getCampingSurroundFac();
            String[] campingSurroundFacArr = campingSurroundFac.split(",");
            List<String> campingSurroundFacList = Stream.of(campingSurroundFacArr).collect(Collectors.toList());
            if(campingSurroundFacList.size() == 1 && campingSurroundFacList.get(0).equals("")) campingSurroundFacList.remove(0);

            responseUserCampingInfoListDto.add(userMapper.toResponseUserCampingInfo(camping,campingEnvList,campingTypeList,campingFacList, campingSurroundFacList, usersId));
        }
        ResponseUserCampingInfoDto responseUserCampingInfoDto = new ResponseUserCampingInfoDto(responseUserCampingInfoListDto);
        return responseUserCampingInfoDto;
    }

    public ResponseUserInfoDto userInfoChange(RequestUserInfoDto requestUserInfoDto,List<String> imgUrl, Users users) {
        if(userRepository.findByNickname(requestUserInfoDto.getNickname()).isPresent()){
            if(requestUserInfoDto.getNickname().equals(users.getNickname())){
                users.change(requestUserInfoDto.getNickname(), imgUrl.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
                userRepository.save(users);
            } else {
                throw new CustomException(DUPLICATE_NICKNAME);
            }
        } else {
            users.change(requestUserInfoDto.getNickname(), imgUrl.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
            userRepository.save(users);
        }
        return userMapper.toResponseUserInfo(users);
    }


    public ResponseReviewOneListDto userReviewInfo(Long usersId) {
        List<Review> reviewList = reviewRepository.findAllByusersId(usersId);
        List<ResponseReviewOneDto> responseReviewOneDtoList = new ArrayList<>();
        for(Review review : reviewList){
            List<Img> urlList = imgRepository.findByReviewId(review.getId());
            List<String> imgList = new ArrayList<>();
            for(Img imgUrl : urlList){
                Img img = new Img(imgUrl);
                imgList.add(img.getImgUrl());
            }
            responseReviewOneDtoList.add(reviewMapper.toResponseReviewOne(review, usersId, imgList));
        }
        ResponseReviewOneListDto responseReviewOneListDto = new ResponseReviewOneListDto(responseReviewOneDtoList);
        return responseReviewOneListDto;
    }

    public ResponseUserReservationDto userReservationInfo(Long usersId) {
        List<Reservation> reservationList = reservationRepository.findAllByUsersId(usersId);
        List<ResponseSearchDto> responseSearchDtoList = new ArrayList<>();

        for(Reservation reservation : reservationList){
            ResponseSearchDto responseSearchDto = reservationMapper.toResponseSearchDto(reservation, reservation.getCamping());
            responseSearchDtoList.add(responseSearchDto);
        }

        ResponseUserReservationDto responseUserReservationDto = new ResponseUserReservationDto(responseSearchDtoList);
        return responseUserReservationDto;
    }
}
