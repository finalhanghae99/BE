package com.product.application.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewLikeResponseDto {
    private Long LikeCount;
    private boolean LikeState;
}
