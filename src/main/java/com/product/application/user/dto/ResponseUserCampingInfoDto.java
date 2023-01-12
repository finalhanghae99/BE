package com.product.application.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseUserCampingInfoDto {
    private List<ResponseUserCampingInfoListDto> responseUserCampingInfoListDtos;
}
