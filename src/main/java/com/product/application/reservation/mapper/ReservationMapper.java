package com.product.application.reservation.mapper;

import com.product.application.camping.entity.Camping;
import com.product.application.reservation.dto.ResponseReservationDto;
import com.product.application.reservation.dto.ResponseSearchDto;
import com.product.application.reservation.entity.Reservation;
import com.product.application.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMapper {
    public ResponseSearchDto entityTo(Reservation reservation, Camping camping, Users users){
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
                .nickname(users.getNickname())
                .profileImageUrl(users.getProfileImageUrl())
                .build();
    }

    public ResponseReservationDto entityToTop6(Reservation reservation, Camping camping) {
        return ResponseReservationDto.builder()
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
}
