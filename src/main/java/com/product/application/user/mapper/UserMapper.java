package com.product.application.user.mapper;

import com.product.application.camping.entity.Camping;
import com.product.application.camping.entity.CampingLike;
import com.product.application.user.dto.RequestSignupDto;
import com.product.application.user.dto.ResponseUserCampingInfoListDto;
import com.product.application.user.dto.ResponseUserInfoDto;
import com.product.application.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    public Users toUser(RequestSignupDto requestSignupDto){
        return Users.builder()
                .useremail(requestSignupDto.getUseremail())
                .nickname(requestSignupDto.getNickname())
                .password(passwordEncoder.encode(requestSignupDto.getPassword()))
                .profileImageUrl(requestSignupDto.getProfileImageUrl())
                .build();
    }

    public ResponseUserInfoDto toResponseUserInfo(Users users) {
        return ResponseUserInfoDto.builder()
                .userId(users.getId())
                .nickname(users.getNickname())
                .profileImageUrl(users.getProfileImageUrl())
                .build();
    }

    public ResponseUserCampingInfoListDto toResponseUserCampingInfo(Camping camping, List<String> campingEnvList, List<String> campingTypeList, List<String> campingFacList, List<String> campingSurroundFacList, Long usersId) {
        CampingLike campingLike = camping.getCampingLikeList().stream().filter(Like -> Like.getUsersId().equals(usersId)).findFirst().orElse(null);
        Boolean likeState = campingLike == null ? false : campingLike.getCampingLikeState();
        return ResponseUserCampingInfoListDto.builder()
                .campingId(camping.getId())
                .imageUrl(camping.getImageUrl())
                .campingName(camping.getCampingName())
                .address1(camping.getAddress1())
                .address2(camping.getAddress2())
                .address3(camping.getAddress3())
                .reviewCount(camping.getReviewCount())
                .campingLikeState(likeState)
                .campingEnv(campingEnvList)
                .campingType(campingTypeList)
                .campingFac(campingFacList)
                .campingSurroundFac(campingSurroundFacList)
                .build();
    }

}
