package com.product.application.camping.dto;

import com.product.application.camping.entity.Camping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
//@NoArgsConstructor
public class ResponseOneCampingInfo {
    private Long campingId;
    private String campingName;
    private String address1;
    private String address2;
    private String address3;
    private String imageUrl;
    private Long reviewCount;
    private Boolean campingLikeState;
    private List<String> campingEnv;
    private List<String> campingType;
    private List<String> campingFac;
    private List<String> campingSurroundFac;

    /*public ResponseOneCampingInfo(Camping camping){
        this.campingId = camping.getId();
        this.campingName = camping.getCampingName();
        this.address1 = camping.getAddress1();
        this.address2 = camping.getAddress2();
        this.address3 = camping.getAddress3();
    }



    @Builder
    public ResponseOneCampingInfo(Long campingId, String campingName, String address1, String address2, String address3){
        this.campingId = campingId;
        this.campingName = campingName;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
    }*/

}
