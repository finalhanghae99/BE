package com.product.application.user.service;

import com.product.application.camping.entity.Camping;
import com.product.application.camping.entity.CampingLike;
import com.product.application.camping.repository.CampingLikeRepository;
import com.product.application.camping.repository.CampingRepository;
import com.product.application.common.exception.CustomException;
import com.product.application.review.dto.ResponseReviewAllDto;
import com.product.application.review.dto.ResponseReviewListDto;
import com.product.application.review.dto.ReviewLikeResponseDto;
import com.product.application.review.entity.Review;
import com.product.application.review.entity.ReviewLike;
import com.product.application.user.dto.ResponseUserCampingInfoDto;
import com.product.application.user.dto.ResponseUserCampingInfoListDto;
import com.product.application.user.dto.ResponseUserInfoDto;
import com.product.application.user.entity.Users;
import com.product.application.user.jwt.JwtUtil;
import com.product.application.user.mapper.UserMapper;
import com.product.application.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.product.application.common.exception.ErrorCode.TOKEN_ERROR;
import static com.product.application.common.exception.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class UserInformationService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final CampingLikeRepository campingLikeRepository;
    private final UserMapper userMapper;

    public ResponseUserInfoDto userInfo(HttpServletRequest request) {
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
            return userMapper.toResponseUserInfo(users);

        } else {
            throw new CustomException(TOKEN_ERROR);
        }
    }

    public ResponseUserCampingInfoDto userCampingInfo(HttpServletRequest request) {
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

            List<CampingLike> campingLikeList = campingLikeRepository.findAllByusersId(usersId);
            List<Camping> CampingList = new ArrayList<>();
            for(CampingLike campingLike : campingLikeList ){
                CampingList.add(campingLike.getCamping());
            }

            List<ResponseUserCampingInfoListDto> responseUserCampingInfoListDto = new ArrayList<>();
            for(Camping camping : CampingList){
                String campingEnv = camping.getCampingEnv();
                String[] campingEnvArr = campingEnv.split(",");
                List<String> campingEnvList = Stream.of(campingEnvArr).collect(Collectors.toList());
                if(campingEnvList.size()==1 && campingEnvList.get(0) == "") campingEnvList.remove(0);

                String campingType = camping.getCampingType();
                String[] campingTypeArr = campingType.split(",");
                List<String> campingTypeList = Stream.of(campingTypeArr).collect(Collectors.toList());
                if(campingTypeList.size() == 1 && campingTypeList.get(0) == "") campingTypeList.remove(0);


                String campingFac = camping.getCampingFac();
                String[] campingFacArr = campingFac.split(",");
                List<String> campingFacList = Stream.of(campingFacArr).collect(Collectors.toList());
                if(campingFacList.size() == 1 && campingFacList.get(0) == "") campingFacList.remove(0);


                String campingSurroundFac = camping.getCampingSurroundFac();
                String[] campingSurroundFacArr = campingSurroundFac.split(",");
                List<String> campingSurroundFacList = Stream.of(campingSurroundFacArr).collect(Collectors.toList());
                if(campingSurroundFacList.size() == 1 && campingSurroundFacList.get(0) == "") campingSurroundFacList.remove(0);

                responseUserCampingInfoListDto.add(userMapper.toResponseUserCampingInfo(camping,campingEnvList,campingTypeList,campingFacList, campingSurroundFacList, usersId));

            }
            ResponseUserCampingInfoDto responseUserCampingInfoDto = new ResponseUserCampingInfoDto(responseUserCampingInfoListDto);
            return responseUserCampingInfoDto;
        } else {
            throw new CustomException(TOKEN_ERROR);
        }
    }
}
