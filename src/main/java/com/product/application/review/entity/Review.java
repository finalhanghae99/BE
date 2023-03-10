package com.product.application.review.entity;

import com.product.application.camping.entity.Camping;
import com.product.application.common.TimeStamped;
import com.product.application.review.dto.RequestReviewWriteDto;
import com.product.application.s3.entity.Img;
import com.product.application.user.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Review extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long id;

    @Column(nullable = false, length = 5000)
    private String content;

    @Column(nullable = false)
    private Long likeCount;

    @Column(nullable = false)
    private Long score1;

    @Column(nullable = false)
    private Long score2;

    @Column(nullable = false)
    private Long score3;

    @Column(nullable = false)
    private Long score4;

    @Column(nullable = false)
    private Long score5;

//    @Column(nullable = false)
//    @ElementCollection
//    private List<String> reviewUrlList;

    @Column(nullable = false)
    @Transient
    private List<Img> reviewUrlList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="usersId")
    private Users users;

    @OneToMany(mappedBy = "review")
    private List<ReviewLike> reviewLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "review")
    private List<Img> imgList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="campingId")
    private Camping camping;

    @Builder
    public Review(Users users, Camping camping, List<Img> reviewUrlList, String content, Long score1, Long score2, Long score3, Long score4, Long score5){
        this.users = users;
        this.camping = camping;
        this.reviewUrlList = reviewUrlList;
        this.content = content;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.score4 = score4;
        this.score5 = score5;
        this.likeCount = 0L;
    }

    public void update(RequestReviewWriteDto requestReviewWriteDto) {
        this.reviewUrlList =  requestReviewWriteDto.getReviewUrlList();
        this.content = requestReviewWriteDto.getContent();
        this.score1 = requestReviewWriteDto.getScore1();
        this.score2 = requestReviewWriteDto.getScore2();
        this.score3 = requestReviewWriteDto.getScore3();
        this.score4 = requestReviewWriteDto.getScore4();
        this.score5 = requestReviewWriteDto.getScore5();
    }

    public void updateLikeCount(boolean state) {
        if(state){
            this.likeCount++;
        }else {
            this.likeCount--;
        }
    }
}
