package com.product.application.review.mapper;

import com.product.application.camping.entity.Camping;
import com.product.application.review.dto.RequestReviewWriteDto;
import com.product.application.review.entity.Review;
import com.product.application.user.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public Review requestReviewWriteDtoToEntity(Users users, Camping camping, RequestReviewWriteDto requestReviewWriteDto){
        return Review.builder()
                .users(users)
                .camping(camping)
                .reviewUrlList(requestReviewWriteDto.getReviewUrlList())
                .content(requestReviewWriteDto.getContent())
                .score1(requestReviewWriteDto.getScore1())
                .score2(requestReviewWriteDto.getScore2())
                .score3(requestReviewWriteDto.getScore3())
                .score4(requestReviewWriteDto.getScore4())
                .score5(requestReviewWriteDto.getScore5())
                .build();
    }

    //MapStruct
}
