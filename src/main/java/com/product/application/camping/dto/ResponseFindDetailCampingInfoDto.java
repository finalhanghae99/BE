package com.product.application.camping.dto;

import com.product.application.camping.entity.Camping;
import com.product.application.review.dto.ResponseDetailCampingInfoReviewDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResponseFindDetailCampingInfoDto {
    private String imageUrl;
    private String campingName;
    private String address1;
    private String address2;
    private String address3;
    private String homepageUrl;
    private String phoneNumber;
    private List<String> campingEnv;
    private List<String> campingType;
    private List<String> campingFac;
    private List<String> campingSurroundFac;
    private String mapX;
    private String mapY;
    private List<ResponseDetailCampingInfoReviewDto> reviewList;

    public ResponseFindDetailCampingInfoDto(Camping camping, List<String> campingEnvList, List<String> campingTypeList, List<String> campingFacList, List<String> campingSurroundFacList){
        this.imageUrl = camping.getImageUrl();
        this.campingName = camping.getCampingName();
        this.address1 = camping.getAddress1();
        this.address2 = camping.getAddress2();
        this.address3 = camping.getAddress3();
        this.homepageUrl = camping.getHomepageUrl();
        this.phoneNumber = camping.getPhoneNumber();
        this.campingEnv = campingEnvList;
        this.campingType = campingTypeList;
        this.campingFac = campingFacList;
        this.campingSurroundFac = campingSurroundFacList;
        this.mapX = camping.getMapX();
        this.mapY = camping.getMapY();
    }

    public void updateReviewDtoList(List<ResponseDetailCampingInfoReviewDto> responseDetailCampingInfoReviewDtoList){
        this.reviewList = responseDetailCampingInfoReviewDtoList;
    }

}
