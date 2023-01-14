package com.product.application.s3;

import com.product.application.review.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Img {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId", nullable = false)
    private Review review;

    public Img(String imgUrl, Review review) {
        this.imgUrl = imgUrl;
        this.review = review;
    }

    public Img(Img imgUrl) {
        this.imgUrl = imgUrl.getImgUrl();
    }
}
