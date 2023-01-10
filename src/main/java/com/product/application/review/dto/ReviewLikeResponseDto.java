package com.product.application.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewLikeResponseDto {
    private Long LikeCount;
    private boolean LikeState;
}
