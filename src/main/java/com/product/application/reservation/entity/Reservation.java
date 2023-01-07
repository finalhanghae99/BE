package com.product.application.reservation.entity;

import com.product.application.camping.entity.Camping;
import com.product.application.reservation.dto.ReservationRequestDto;
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
    private boolean tradeState = true;

    @Column(nullable = false)
    private Long price;

    @ManyToOne
    @JoinColumn(name="usersId")
    private Users users;

    @ManyToOne
    @JoinColumn(name="campingId")
    private Camping camping;

    public Reservation(ReservationRequestDto reservationRequestDto, Users users) {
        this.content = reservationRequestDto.getContent();
        this.startDate = reservationRequestDto.getStartDate();
        this.endDate = reservationRequestDto.getEndDate();
        this.price = reservationRequestDto.getPrice();
        this.tradeState = reservationRequestDto.isTradeState();
        this.users = users;

    }



}

