package com.product.application.camping.entity;

import com.product.application.reservation.entity.Reservation;
import com.product.application.review.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Camping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campingId")
    private Long id;

    @Column(nullable = false)
    private String campingName;

    @Column
    private String address1;

    @Column
    private String address2;

    @Column
    private String address3;

    @Column
    private String campingEnv;

    @Column
    private String campingFac;

    @Column
    private String mapX;

    @Column
    private String mapY;

    @Column
    private String campingType;

    @Column
    private String homepageUrl;

    @Column
    private String phoneNumber;

    @Column
    private String campingSurroundFac; // 주변이용시설

    @Column
    private String createdAt; // open api에 등록된 생성시간

    @Column
    private String modifiedAt; // open api에 등록된 수정시간

    @Column
    private Long campingLikeCount;

    @Column
    private Long reviewCount;

    @OneToMany(mappedBy = "camping")
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "camping")
    private List<Reservation> reservationList = new ArrayList<>();

    @OneToMany(mappedBy = "camping")
    private List<CampingLike> campingLikeList = new ArrayList<>();
}
