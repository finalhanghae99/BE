package com.product.application.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ResponseUserCampingInfoListDto {
    private Long campingId;
    private String imageUrl;
    private String campingName;
    private String address1;
    private String address2;
    private String address3;
    private Long reviewCount;
    private Boolean campingLikeState;
    private List<String> campingEnv;
    private List<String> campingType;
    private List<String> campingFac;
    private List<String> campingSurroundFac;
}
