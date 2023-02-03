package com.product.application.reservation.mapper;

import com.product.application.camping.entity.Camping;
import com.product.application.reservation.dto.RequestReservationDto;
import com.product.application.reservation.dto.ResponseReservationDto;
import com.product.application.reservation.dto.ResponseSearchDto;
import com.product.application.reservation.entity.Reservation;
import com.product.application.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMapper {

    public Reservation toRequestReservationDto(Users users, Camping camping, RequestReservationDto requestReservationDto){
        return Reservation.builder()
                .users(users)
                .camping(camping)
                .content(requestReservationDto.getContent())
                .tradeState(requestReservationDto.isTradeState())
                .startDate(requestReservationDto.getStartDate())
                .endDate(requestReservationDto.getEndDate())
                .price(requestReservationDto.getPrice())
                .build();
    }

    public ResponseSearchDto toResponseSearchDto(Reservation reservation, Camping camping){
        return ResponseSearchDto.builder()
                .reservationId(reservation.getId())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .imageUrl(camping.getImageUrl())
                .campingName(camping.getCampingName())
                .address1(camping.getAddress1())
                .address2(camping.getAddress2())
                .tradeState(reservation.isTradeState())
                .price(reservation.getPrice())
                .build();
    }

    public ResponseReservationDto toresponseReservationDto(Reservation reservation, Camping camping, Users users, Long usersId) {
        boolean owncheck = users.getId().equals(usersId) == true ? true : false;
        return ResponseReservationDto.builder()
                .reservationId(reservation.getId())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .imageUrl(camping.getImageUrl())
                .campingName(camping.getCampingName())
                .campingId(camping.getId())
                .address3(camping.getAddress3())
                .tradeState(reservation.isTradeState())
                .price(reservation.getPrice())
                .content(reservation.getContent())
                .nickname(users.getNickname())
                .profileImageUrl(users.getProfileImageUrl())
                .ownerCheck(owncheck)
                .build();
    }
}
