package com.product.application.camping.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseCampingFiveListDto {
    private List<ResponseCampingFiveDto> responseCampingFiveDtos;
}
