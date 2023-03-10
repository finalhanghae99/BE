package com.product.application.camping.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Getter
@NoArgsConstructor
@Entity
public class CampingLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usersId;

    private Boolean campingLikeState;

    @ManyToOne
    @JoinColumn(name = "campingId")
    private Camping camping;

    public CampingLike(Long usersId, Boolean campingLikeState, Camping camping){
        this.usersId = usersId;
        this.campingLikeState = campingLikeState;
        this.camping = camping;
    }

    public void stateUpdate(Boolean campingLikeState){
        this.campingLikeState = campingLikeState;
    }
}
