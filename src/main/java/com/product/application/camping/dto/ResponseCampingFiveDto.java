package com.product.application.camping.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseCampingFiveDto {
    private Long campingId;
    private String imageUrl;
    private String campingName;
    private String address1;
    private String address2;
    private String address3;
}
