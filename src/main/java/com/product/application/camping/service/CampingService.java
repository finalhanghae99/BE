package com.product.application.camping.service;

import com.product.application.camping.dto.ResponseOneCampingInfo;
import com.product.application.camping.entity.Camping;
import com.product.application.camping.mapper.CampingMapper;
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
    private final CampingMapper campingMapper;
    public ResponseMessage searchAllCampingInfo(String campingname, String address1, String address2, HttpServletRequest request) {
        List<Camping> campingList;
        List<Camping> returnCampingList = new ArrayList<>();
        // # 검색기능 구현
        /*
            1. 총 8가지 경우로 구분
                1.1. campingname X address1 X address2 X -> Error 반환
                1.2. campingname X address1 X address2 O -> Error 반환
                1.3. campingname X address1 O address2 X -> 검색가능
                1.4. campingname X address1 O address2 O -> 검색가능
                1.5. campingname O address1 X address2 X -> 검색가능
                1.6. campingname O address1 X address2 O -> Error 반환
                1.7. campingname O address1 O address2 X -> 검색가능
                1.8. campingname O address1 O address2 O -> 검색가능
         */
        // 1.경우나누기 -> 에러 반환 및 List<Camping> 추출
        // 1.1. campingname X address1 X address2 X
        if(campingname == null && address1 == null && address2 == null){
            throw new CustomException(ErrorCode.REQUIRED_AT_LEAST_ONE);
        }
        // 1.2. campingname X address1 X address2 O -> 에러반환
        else if(campingname == null && address1 == null && address2 != null){
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }
        // 1.3. campingname X address1 O address2 X -> 검색가능
        else if(campingname == null && address1 != null && address2 == null){
            returnCampingList = campingRepository.findAllByAddress1(address1);
        }
        // 1.4. campingname X address1 O address2 O -> 검색가능
        // -> @Query? + sql쿼리문 함께 보기? > innerJoin / QueryDsl?
        else if(campingname == null && address1 != null && address2 != null){
            campingList = campingRepository.findAllByAddress1(address1);
            for(Camping camping : campingList){
                if(camping.getAddress2().equals(address2)){
                    returnCampingList.add(camping);
                }
            }
        }
        // 1.5. campingname O address1 X address2 X -> 검색가능
        else if(campingname != null && address1 == null && address2 == null){
            returnCampingList = campingRepository.findAllByCampingNameContaining(campingname);
        }
        // 1.6. campingname O address1 X address2 O -> 에러반환
        else if(campingname != null && address1 == null && address2 != null){
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }
        // 1.7. campingname O address1 O address2 X -> 검색가능
        else if(campingname != null && address1 != null && address2 == null){
            // campingname으로 디비에서 받아오기 -> address1이 일치하는 것만 리스트에 받아서 반환
            campingList = campingRepository.findAllByCampingNameContaining(campingname);
            for(Camping camping : campingList){
                if(camping.getAddress1().equals(address1)){
                    returnCampingList.add(camping);
                }
            }
        }
        // 1.8. campingname O address1 O address2 O -> 검색가능
        else if(campingname != null && address1 != null && address2 != null){
            // campingname으로 추려내고 address1 & address2가 일치하는 것을 반환한다
            campingList = campingRepository.findAllByCampingNameContaining(campingname);
            for(Camping camping : campingList){
                if(camping.getAddress1().equals(address1) && camping.getAddress2().equals(address2)){
                    returnCampingList.add(camping);
                }
            }
        }
        // 2.추출된 List<Camping> returnCampingList에서 값을 추출해서 List<ResponseOneCampingInfo>만들기
        List<ResponseOneCampingInfo> responseOneCampingInfoList = new ArrayList<>();
        for(Camping camping : returnCampingList){
            ResponseOneCampingInfo responseOneCampingInfo = campingMapper.entityToResponseOneCampingInfo(camping);
            responseOneCampingInfoList.add(responseOneCampingInfo);
        }
        return new ResponseMessage("Success", 200, responseOneCampingInfoList);
    }
}
