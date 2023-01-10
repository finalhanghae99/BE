package com.product.application.reservation.mapper;

import com.product.application.camping.entity.Camping;
import com.product.application.reservation.dto.ResponseSearchDto;
import com.product.application.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMapper {
    public ResponseSearchDto entityTo(Reservation reservation, Camping camping){
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
}
