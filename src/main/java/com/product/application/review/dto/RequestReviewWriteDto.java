package com.product.application.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class RequestReviewWriteDto {
    private List<String> reviewUrlList;
    private String content;
    private Long score1;
    private Long score2;
    private Long score3;
    private Long score4;
    private Long score5;
}
