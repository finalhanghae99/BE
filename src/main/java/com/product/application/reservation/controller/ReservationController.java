package com.product.application.reservation.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.reservation.dto.ReservationRequestDto;
import com.product.application.reservation.service.ReservationService;
import com.product.application.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping()
    public ResponseMessage<?> create(@RequestBody ReservationRequestDto reservationRequestDto
                                     , HttpServletRequest request) {
        reservationService.create(reservationRequestDto, request);
        return new ResponseMessage<>("Success", 200, null);

    }
}
