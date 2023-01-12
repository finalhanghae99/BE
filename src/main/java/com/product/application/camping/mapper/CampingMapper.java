package com.product.application.camping.mapper;

import com.product.application.camping.dto.ResponseCampingFiveDto;
import com.product.application.camping.dto.ResponseOneCampingInfo;
import com.product.application.camping.entity.Camping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CampingMapper {
    public ResponseOneCampingInfo entityToResponseOneCampingInfo(Camping camping) {
        return ResponseOneCampingInfo.builder()
                .campingId(camping.getId())
                .campingName(camping.getCampingName())
                .address1(camping.getAddress1())
                .address2(camping.getAddress2())
                .address3(camping.getAddress3())
                .build();
    }

    public ResponseCampingFiveDto toResponseCampingFive(Camping camping) {
        return ResponseCampingFiveDto.builder()
                .campingId(camping.getId())
                .campingName(camping.getCampingName())
                .imageUrl(camping.getImageUrl())
                .address1(camping.getAddress1())
                .address2(camping.getAddress2())
                .address3(camping.getAddress3())
                .build();
    }
}
