package com.product.application.review.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ReviewLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column
    private Boolean likeState;
    @ManyToOne
    @JoinColumn(name = "reviewId")
    private Review review;

    public ReviewLike(Long usersId, Review review, Boolean likeState) {
        this.userId = usersId;
        this.review = review;
        this.likeState = likeState;
    }

    public void update(boolean likeState) {
        this.likeState = likeState;
    }
}
