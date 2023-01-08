package com.product.application.camping.dto;

import com.product.application.camping.entity.Camping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseFindListFiveDto {
    private Long campingId;
    private String imageUrl;
    private String campingName;
    private String address1;
    private String address2;
    private String address3;

    public ResponseFindListFiveDto(Camping camping){
        this.campingId = camping.getId();
        this.imageUrl = camping.getImageUrl();
        this.campingName = camping.getCampingName();
        this.address1 = camping.getAddress1();
        this.address2 = camping.getAddress2();
        this.address3 = camping.getAddress3();
    }
}
