package com.product.application.reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ReservationRequestDto {
    public String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate endDate;
    public boolean tradeState = true;
    public Long price;




}
