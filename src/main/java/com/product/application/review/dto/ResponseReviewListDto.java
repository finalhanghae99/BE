package com.product.application.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ResponseReviewListDto {
    private Long reviewId;
    private String campingName;
    private List<String> reviewUrlList;
    private String nickname;
    private LocalDateTime modifiedAt;
    private String content;
    private Long likeCount;
    private Boolean likeState;
    private String profileImageUrl;
}
