package com.product.application.reservation.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.reservation.dto.RequestReservationDto;
import com.product.application.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/{campingId}")
    public ResponseMessage<?> create(@RequestBody RequestReservationDto requestReservationDto
                                     , HttpServletRequest request
                                     , @PathVariable Long campingId) {
        reservationService.create(requestReservationDto, request, campingId);
        return new ResponseMessage<>("Success", 200, null);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseMessage<?> delete(@PathVariable Long reservationId
                                     , HttpServletRequest request) {
        reservationService.delete(reservationId, request);
        return new ResponseMessage<>("Success", 200, null);
    }

    @GetMapping()
    public ResponseMessage getReservationList(@RequestParam(value="startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                              @RequestParam(value="endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                              @RequestParam(value="address1", required = false) String address1,
                                              @RequestParam(value = "address2", required = false) String address2,
                                              HttpServletRequest request) {
        ResponseMessage responseMessage = reservationService.getReservationList(startDate, endDate, address1, address2, request);
        return responseMessage;
    }

    @GetMapping("/{reservationId}")
    public ResponseMessage getReservation(@PathVariable Long reservationId, HttpServletRequest request) {
        ResponseMessage responseMessage = reservationService.getReservation(reservationId, request);
        return responseMessage;
    }

}
