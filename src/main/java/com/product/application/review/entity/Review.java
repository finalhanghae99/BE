package com.product.application.review.entity;

import com.product.application.campinginfo.entity.Camping;
import com.product.application.common.TimeStamped;
import com.product.application.user.entity.Users;
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

    @Column(nullable = false)
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

    @Column(nullable = false)
    private Long reviewUrl;

    @ManyToOne
    @JoinColumn(name="usersId")
    private Users users;

    @OneToMany(mappedBy = "review")
    private List<ReviewLike> reviewLikeList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="campingId")
    private Camping camping;
}
