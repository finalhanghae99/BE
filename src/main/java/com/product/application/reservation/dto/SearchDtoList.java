package com.product.application.reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchDtoList {
    private List<ResponseSearchDto> responseSearchDtoList;
    public SearchDtoList(List<ResponseSearchDto> responseSearchDtoList) {
        this.responseSearchDtoList = responseSearchDtoList;
    }
}
