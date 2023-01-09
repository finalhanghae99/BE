package com.product.application.reservation.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.reservation.dto.RequestReservationDto;
import com.product.application.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping()
    public ResponseMessage<?> create(@RequestBody RequestReservationDto requestReservationDto
                                     , HttpServletRequest request) {
        reservationService.create(requestReservationDto, request);
        return new ResponseMessage<>("Success", 200, null);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseMessage<?> delete(@PathVariable Long reservationId
                                     , HttpServletRequest request) {
        reservationService.delete(reservationId, request);
        return new ResponseMessage<>("Success", 200, null);
    }

}
