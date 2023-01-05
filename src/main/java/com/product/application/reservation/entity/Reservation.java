package com.product.application.reservation.entity;

import com.product.application.campinginfo.entity.CampingInfo;
import com.product.application.user.entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private boolean tradeState;

    @Column(nullable = false)
    private Long price;

    @ManyToOne
    @JoinColumn(name="userId")
    private Users users;

    @ManyToOne
    @JoinColumn(name="campingId")
    private CampingInfo campingInfo;
}

