package com.product.application.reservation.dto;

import com.product.application.camping.entity.Camping;
import com.product.application.reservation.entity.Reservation;
import com.product.application.user.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ResponseSearchDto {

    private Long reservationId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String imageUrl;
    private String campingName;
    private String address1;
    private String address2;
    private boolean tradeState;
    private Long price;

    public ResponseSearchDto(Reservation reservation, Camping camping){
        this.reservationId = reservation.getId();
        this.startDate = reservation.getStartDate();
        this.endDate = reservation.getEndDate();
        this.imageUrl = camping.getImageUrl();
        this.campingName = camping.getCampingName();
        this.address1 = camping.getAddress1();
        this.address2 =camping.getAddress2();
        this.tradeState = reservation.isTradeState();
        this.price = reservation.getPrice();

    }

    @Builder
    public ResponseSearchDto(Long reservationId, LocalDate startDate, LocalDate endDate, String imageUrl, String campingName, String address1, String address2, boolean tradeState, Long price){
        this.reservationId = reservationId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.imageUrl = imageUrl;
        this.campingName = campingName;
        this.address1 = address1;
        this.address2 = address2;
        this.tradeState = tradeState;
        this.price = price;


    }
}
