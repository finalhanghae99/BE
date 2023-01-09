package com.product.application.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseReviewAllDto {
    private List<ResponseReviewListDto> responseReviewListDtos;
}
