package com.product.application.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseReviewSixDto {
    private String campingName;
    private Long score1;
    private Long score2;
    private Long score3;
    private Long score4;
    private Long score5;
}
