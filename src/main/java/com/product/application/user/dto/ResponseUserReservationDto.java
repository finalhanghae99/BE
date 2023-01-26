package com.product.application.user.dto;

import com.product.application.reservation.dto.ResponseSearchDto;
import com.product.application.review.dto.ResponseReviewListDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseUserReservationDto {
    private List<ResponseSearchDto> responseSearchDtoList;
}
