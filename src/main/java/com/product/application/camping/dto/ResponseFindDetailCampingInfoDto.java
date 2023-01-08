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

    //private String tagCategory;
    private String campingEnv;
    private String campingType;
    private String campingFac;
    private String campingSurroundFac;
    private String mapX;
    private String mapY;
    private List<ResponseDetailCampingInfoReviewDto> responseDetailCampingInfoReviewDtoList;

    public ResponseFindDetailCampingInfoDto(Camping camping){
        this.imageUrl = camping.getImageUrl();
        this.campingName = camping.getCampingName();
        this.address1 = camping.getAddress1();
        this.address2 = camping.getAddress2();
        this.address3 = camping.getAddress3();
        this.homepageUrl = camping.getHomepageUrl();
        this.phoneNumber = camping.getPhoneNumber();
        this.campingEnv = camping.getCampingEnv();
        this.campingType = camping.getCampingType();
        this.campingFac = camping.getCampingSurroundFac();
        this.mapX = camping.getMapX();
        this.mapY = camping.getMapY();
    }

    public void updateReviewDtoList(List<ResponseDetailCampingInfoReviewDto> responseDetailCampingInfoReviewDtoList){
        this.responseDetailCampingInfoReviewDtoList = responseDetailCampingInfoReviewDtoList;
    }

}
