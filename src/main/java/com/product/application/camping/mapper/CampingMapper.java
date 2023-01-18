package com.product.application.camping.mapper;

import com.product.application.camping.dto.ResponseCampingFiveDto;
import com.product.application.camping.dto.ResponseOneCampingInfo;
import com.product.application.camping.entity.Camping;
import com.product.application.camping.entity.CampingLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CampingMapper {
    public ResponseOneCampingInfo entityToResponseOneCampingInfo(Camping camping, List<String> campingEnvList, List<String> campingTypeList, List<String> campingFacList, List<String> campingSurroundFacList, Long usersId) {
        CampingLike campingLike = camping.getCampingLikeList().stream().filter(Like -> Like.getUsersId().equals(usersId)).findFirst().orElse(null);
        Boolean likeState = campingLike == null ? false : campingLike.getCampingLikeState();
        return ResponseOneCampingInfo.builder()
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

    public ResponseCampingFiveDto toResponseCampingFive(Camping camping) {
        return ResponseCampingFiveDto.builder()
                .campingId(camping.getId())
                .campingName(camping.getCampingName())
                .imageUrl(camping.getImageUrl())
                .address1(camping.getAddress1())
                .address2(camping.getAddress2())
                .address3(camping.getAddress3())
                .build();
    }
}
