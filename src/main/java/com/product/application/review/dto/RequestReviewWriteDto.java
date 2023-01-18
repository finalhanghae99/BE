package com.product.application.review.dto;

import com.product.application.s3.entity.Img;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class RequestReviewWriteDto {
    private List<Img> reviewUrlList;
    private String content;
    private Long score1;
    private Long score2;
    private Long score3;
    private Long score4;
    private Long score5;
}
