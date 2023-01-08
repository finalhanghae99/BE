package com.product.application.camping.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RequestFindListFiveDto {
    List<Long> campingIdList;
}
