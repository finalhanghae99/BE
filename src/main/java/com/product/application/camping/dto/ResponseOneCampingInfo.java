package com.product.application.camping.dto;

import com.product.application.camping.entity.Camping;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ResponseOneCampingInfo {
    private Long campingId;
    private String campingName;
    private String address1;
    private String address2;
    private String address3;

    public ResponseOneCampingInfo(Camping camping){
        this.campingId = camping.getId();
        this.campingName = camping.getCampingName();
        this.address1 = camping.getAddress1();
        this.address2 = camping.getAddress2();
        this.address3 = camping.getAddress3();
    }

}
