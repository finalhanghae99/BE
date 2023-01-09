package com.product.application.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RequestFindListTenDto {
    List<Long> campingIdList;
}
