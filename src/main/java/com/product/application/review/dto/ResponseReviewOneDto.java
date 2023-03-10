package com.product.application.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ResponseReviewOneDto {
    private Long reviewId;
    private String campingName;
    private Long campingId;
    private String nickname;
    private Long score1;
    private Long score2;
    private Long score3;
    private Long score4;
    private Long score5;
    private LocalDateTime modifiedAt;
    private String content;
    private Long likeCount;
    private Boolean likeState;
    private List<String> reviewUrlList;
    private boolean ownerCheck;
    private String profileImageUrl;
}