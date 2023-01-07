package com.product.application.camping.service;

import com.product.application.camping.dto.ResponseOneCampingInfo;
import com.product.application.camping.entity.Camping;
import com.product.application.camping.repository.CampingRepository;
import com.product.application.common.ResponseMessage;
import com.product.application.common.exception.CustomException;
import com.product.application.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampingService {
    private final CampingRepository campingRepository;
    public ResponseMessage searchAllCampingInfo(String campingname, String address1, String address2, HttpServletRequest request) {
        List<Camping> campingList;
        // # 검색기능 구현
        // 1.경우나누기 -> 에러 반환 및 List<Camping> 추출
        // 1.1. campingname X address1 X address2 X  -> 에러반환
        if(campingname == null && address1 == null && address2 == null){
            throw new CustomException(ErrorCode.REQUIRED_AT_LEAST_ONE);
        }
        // 1.2. campingname X address1 X address2 O
        else if(campingname == null && address1 == null && address2 != null){
            campingList = campingRepository.findAllByAddress2(address2);
        }
        // 1.3. campingname X address1 O address2 X - 0
        else if(campingname == null && address1 != null && address2 == null){
            campingList = campingRepository.findAllByAddress1(address1);
        }
        // 1.4. campingname X address1 O address2 O - 0
        // -> @Query? + sql쿼리문 함께 보기? > innerJoin / QueryDsl?
        else if(campingname == null && address1 != null && address2 != null){
            campingList = campingRepository.findAllByAddress1AndAddress2(address1,address2);
        }
        // 1.5. campingname O address1 X address2 X
        else if(campingname != null && address1 == null && address2 == null){
            campingList = campingRepository.findAllByCampingNameContaining(campingname);
        }
        // 1.6. campingname O address1 X address2 O
        else if(campingname != null && address1 == null && address2 != null){
            List<Camping> campings = campingRepository.findAllByCampingNameContaining(campingname);
            for(Camping camping : campings){

            }
        }
        // 1.7. campingname O address1 O address2 X
        else if(campingname != null && address1 != null && address2 == null){
            // address1으로 검색하고 campingname으로 검색

        }
        // 1.8. campingname O address1 O address2 O
        else if(campingname != null && address1 != null && address2 != null){
            // address1으로 검색하고 address2로 추려내고 campingname으로 추려낸다.
        }

        // 2.추출된 List<Camping>에서 값을 추출해서 List<ResponseOneCampingInfo>만들기
        List<ResponseOneCampingInfo> responseOneCampingInfoList = new ArrayList<>();





        return new ResponseMessage("Success", 200, responseOneCampingInfoList);
    }
}
