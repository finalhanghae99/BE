package com.product.application.camping.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ResponseFindListFiveDto {
    private Long campingId;
    private String imageUrl;
    private String campingName;
    private String address1;
    private String address2;
    private String address3;

    @Builder
    public ResponseFindListFiveDto(Long campingId, String imageUrl, String campingName, String address1, String address2, String address3){
        this.campingId = campingId;
        this.imageUrl = imageUrl;
        this.campingName = campingName;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
    }
}
