package com.product.application.reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class RequestSearchDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String address1;
    private String address2;


    public void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate);
    }
    public void setEndDate(String endDate) {
        this.endDate = LocalDate.parse(endDate);
    }



}
