package com.product.application.reservation.entity;

import com.product.application.camping.entity.Camping;
import com.product.application.reservation.dto.RequestReservationDto;
import com.product.application.user.entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    public Reservation(RequestReservationDto requestReservationDto, Users users, Camping camping) {
        this.content = requestReservationDto.getContent();
        this.startDate = requestReservationDto.getStartDate();
        this.endDate = requestReservationDto.getEndDate();
        this.price = requestReservationDto.getPrice();
        this.tradeState = requestReservationDto.isTradeState();
        this.users = users;
        this.camping = camping;
    }



}

