package com.product.application.review.mapper;

import com.product.application.camping.entity.Camping;
import com.product.application.review.dto.RequestReviewWriteDto;
import com.product.application.review.dto.ResponseReviewListDto;
import com.product.application.review.dto.ResponseReviewOneDto;
import com.product.application.review.dto.ResponseReviewSixDto;
import com.product.application.review.entity.Review;
import com.product.application.review.entity.ReviewLike;
import com.product.application.user.entity.Users;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public ResponseReviewListDto toResponseReviewListDto(Review review, Long usersId, List<String> imgList) {
        ReviewLike reviewLike = review.getReviewLikeList().stream().filter(Like -> Like.getUserId().equals(usersId)).findFirst().orElse(null);  //userId뿐만 아니라 review아이디도 같아야 한다
        Boolean likestate = reviewLike == null ? false : reviewLike.getLikeState();
        return ResponseReviewListDto.builder()
                .reviewId(review.getId())
                .campingName(review.getCamping().getCampingName())
                .reviewUrlList(imgList)       //리뷰 URL 리스트에 있는 값들을 다 가져와야 한다.
                .nickname(review.getUsers().getNickname())
                .modifiedAt(review.getModifiedAt())
                .content(review.getContent())
                .likeCount(review.getLikeCount())
                .likeState(likestate)
                .profileImageUrl(review.getUsers().getProfileImageUrl())
                .build();
    }

    public ResponseReviewOneDto toResponseReviewOne(Review review, Long usersId, List<String> imgList) {
        ReviewLike reviewLike = review.getReviewLikeList().stream().filter(Like -> Like.getUserId().equals(usersId)).findFirst().orElse(null);  //userId뿐만 아니라 review아이디도 같아야 한다
        Boolean likestate = reviewLike == null ? false : reviewLike.getLikeState();
        Boolean owncheck = review.getUsers().getId().equals(usersId) == true ? true : false;
        return ResponseReviewOneDto.builder()
                .reviewId(review.getId())
                .campingName(review.getCamping().getCampingName())
                .nickname(review.getUsers().getNickname())
                .score1(review.getScore1())
                .score2(review.getScore2())
                .score3(review.getScore3())
                .score4(review.getScore4())
                .score5(review.getScore5())
                .modifiedAt(review.getModifiedAt())
                .content(review.getContent())
                .likeCount(review.getLikeCount())
                .likeState(likestate)
                .reviewUrlList(imgList)       //리뷰 URL 리스트에 있는 값들을 다 가져와야 한다.
                .ownerCheck(owncheck)
                .profileImageUrl(review.getUsers().getProfileImageUrl())
                .build();
    }


    public ResponseReviewSixDto toResponseReviewSix(Review review) {
        return ResponseReviewSixDto.builder()
                .campingName(review.getCamping().getCampingName())
                .score1(review.getScore1())
                .score2(review.getScore2())
                .score3(review.getScore3())
                .score4(review.getScore4())
                .score5(review.getScore5())
                .imageUrl(review.getCamping().getImageUrl())
                .reviewId(review.getId())
                .build();
    }
}
