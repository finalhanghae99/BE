package com.product.application.review.dto;

import com.product.application.review.entity.Review;
import com.product.application.s3.Img;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ResponseDetailCampingInfoReviewDto {
    private String nickname;
    private LocalDateTime modifiedAt;
    private String content;
    private List<String> reviewUrlList;

    public ResponseDetailCampingInfoReviewDto(Review review, List<String> imgList){
        this.nickname = review.getUsers().getNickname();
        this.modifiedAt = review.getModifiedAt();
        this.content = review.getContent();
        this.reviewUrlList = imgList;
        // * this.url을 외부에서 입력받는 이유 *
        // review에는 reviewUrl이 list형태로 존재하고 있다.(review.getReviewUrlList()로 리스트를 불러올 수 있음)
        // 여기(dto)에서 list에서 한장을 뽑아서 저장하는 처리를 하는 것은 Review엔티티에 대한 종속성이 너무 커진다.
        // 따라서 외부에서 review엔티티의 getReviewUrlList()를 통해서 사진 한장을 뽑아서 imageUrl을 String으로 전달한다.
        // 또한 만약 리뷰에 이미지 리스트가 없는 경우는 null로 프론트엔드에 보낸다.
    }


}
