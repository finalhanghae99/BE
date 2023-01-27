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
public class ResponseReservationDto {
    private Long reservationId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String imageUrl;
    private String campingName;
    private String address3;
    private boolean tradeState;
    private Long price;
    private String content;

    private String nickname;
    private String profileImageUrl;
    private boolean ownerCheck;

    public ResponseReservationDto(Reservation reservation, Camping camping, Users users){
        this.reservationId = reservation.getId();
        this.startDate = reservation.getStartDate();
        this.endDate = reservation.getEndDate();
        this.imageUrl = camping.getImageUrl();
        this.campingName = camping.getCampingName();
        this.address3 = camping.getAddress3();
        this.tradeState = reservation.isTradeState();
        this.price = reservation.getPrice();
        this.content = reservation.getContent();
        this.nickname = users.getNickname();
        this.profileImageUrl = users.getProfileImageUrl();
    }

    @Builder
    public ResponseReservationDto(Long reservationId,LocalDate startDate, LocalDate endDate, String imageUrl, String campingName, String address3, boolean tradeState, Long price, String content, String nickname, String profileImageUrl, boolean ownerCheck){
        this.reservationId = reservationId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.imageUrl = imageUrl;
        this.campingName = campingName;
        this.address3 = address3;
        this.tradeState = tradeState;
        this.price = price;
        this.content = content;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.ownerCheck = ownerCheck;
    }


}
