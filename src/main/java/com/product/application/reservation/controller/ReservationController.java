package com.product.application.reservation.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.reservation.dto.RequestReservationDto;
import com.product.application.reservation.service.ReservationService;
import com.product.application.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/{campingId}")
    public ResponseMessage<?> create(@RequestBody RequestReservationDto requestReservationDto
            , @AuthenticationPrincipal UserDetailsImpl userDetails
            , @PathVariable Long campingId) {
        reservationService.create(requestReservationDto, userDetails.getUser(), campingId);
        return new ResponseMessage<>("Success", 200, null);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseMessage<?> delete(@PathVariable Long reservationId
            , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long usersId = userDetails.getUserId();
        reservationService.delete(reservationId, usersId);
        return new ResponseMessage<>("Success", 200, null);
    }

    @GetMapping("/findall")
    public ResponseMessage getReservationList(@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                              @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                              @RequestParam(value = "address1", required = false) String address1,
                                              @RequestParam(value = "address2", required = false) String address2) {
        ResponseMessage responseMessage = reservationService.getReservationList(startDate, endDate, address1, address2);
        return responseMessage;
    }

    @GetMapping("/{reservationId}")
    public ResponseMessage getReservation(@PathVariable Long reservationId,  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long usersId;
        if(userDetails != null){
            usersId = userDetails.getUserId();
        } else {
            usersId = 0L;
        }
        ResponseMessage responseMessage = reservationService.getReservation(reservationId, usersId);
        return responseMessage;
    }

    @GetMapping("/listsix")
    public ResponseMessage viewListSix() {
        ResponseMessage responseMessage = reservationService.viewListSix();
        return responseMessage;
    }

    @PostMapping("/changestate/{reservationId}")
    public ResponseMessage<?> updateState(@PathVariable Long reservationId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long usersId = userDetails.getUserId();
        reservationService.updateState(reservationId, usersId);
        return new ResponseMessage<>("Success", 200, null);
    }

    @PutMapping("/{reservationId}")
    public ResponseMessage<?> updateReservation(@RequestBody RequestReservationDto requestReservationDto
            , @AuthenticationPrincipal UserDetailsImpl userDetails
            , @PathVariable Long reservationId) {
        reservationService.updateReservation(requestReservationDto, userDetails.getUser(), reservationId);
        return new ResponseMessage<>("Success", 200, null);
    }
}
