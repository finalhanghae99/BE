package com.product.application.review.dto;

import com.product.application.camping.entity.Camping;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseFindListTenDto {
    private Long campingId;
    private String imageUrl;
    private String campingName;
    private String address1;
    private String address2;
    private String address3;
    private Long reviewCount;
    private boolean campingLikeState;
    private String campingEnv;
    private String campingType;
    private String campingFac;
    private String campingSurroundFac;
    public ResponseFindListTenDto(Camping camping, Long reviewCount, boolean campingLikeState){
        this.campingId = camping.getId();
        this.imageUrl = camping.getImageUrl();
        this.campingName = camping.getCampingName();
        this.address1 = camping.getAddress1();
        this.address2 = camping.getAddress2();
        this.address3 = camping.getAddress3();
        this.reviewCount = reviewCount;
        this.campingLikeState = campingLikeState;
        this.campingEnv = camping.getCampingEnv();
        this.campingType = camping.getCampingType();
        this.campingFac = camping.getCampingFac();
        this.campingSurroundFac = camping.getCampingSurroundFac();
    }
}
