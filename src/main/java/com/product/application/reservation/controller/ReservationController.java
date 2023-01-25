package com.product.application.reservation.controller;

import com.product.application.common.ResponseMessage;
import com.product.application.reservation.dto.RequestReservationDto;
import com.product.application.reservation.service.ReservationService;
import com.product.application.user.jwt.JwtUtil;
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

    @CrossOrigin(origins = {"http://campingzipbeta.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:3000"}, exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @PostMapping("/{campingId}")
    public ResponseMessage<?> create(@RequestBody RequestReservationDto requestReservationDto
            , HttpServletRequest request
            , @PathVariable Long campingId) {
        reservationService.create(requestReservationDto, request, campingId);
        return new ResponseMessage<>("Success", 200, null);
    }

    @CrossOrigin(origins = {"http://campingzipbeta.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:3000"}, exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @DeleteMapping("/{reservationId}")
    public ResponseMessage<?> delete(@PathVariable Long reservationId
            , HttpServletRequest request) {
        reservationService.delete(reservationId, request);
        return new ResponseMessage<>("Success", 200, null);
    }

    @CrossOrigin(origins = {"http://campingzipbeta.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:3000"}, exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @GetMapping()
    public ResponseMessage getReservationList(@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                              @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                              @RequestParam(value = "address1", required = false) String address1,
                                              @RequestParam(value = "address2", required = false) String address2) {
        ResponseMessage responseMessage = reservationService.getReservationList(startDate, endDate, address1, address2);
        return responseMessage;
    }

    @CrossOrigin(origins = {"http://campingzipbeta.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:3000"}, exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @GetMapping("/{reservationId}")
    public ResponseMessage getReservation(@PathVariable Long reservationId) {
        ResponseMessage responseMessage = reservationService.getReservation(reservationId);
        return responseMessage;
    }

    @CrossOrigin(origins = {"http://campingzipbeta.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:3000"}, exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @GetMapping("/listsix")
    public ResponseMessage viewListSix() {
        ResponseMessage responseMessage = reservationService.viewListSix();
        return responseMessage;
    }
}
