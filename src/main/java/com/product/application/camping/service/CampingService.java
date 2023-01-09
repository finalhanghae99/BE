package com.product.application.camping.service;

import com.product.application.camping.dto.ResponseFindDetailCampingInfoDto;
import com.product.application.camping.dto.ResponseFindListFiveDto;
import com.product.application.camping.dto.ResponseOneCampingInfo;
import com.product.application.camping.entity.Camping;
import com.product.application.camping.mapper.CampingMapper;
import com.product.application.camping.repository.CampingRepository;
import com.product.application.common.ResponseMessage;
import com.product.application.common.exception.CustomException;
import com.product.application.common.exception.ErrorCode;
import com.product.application.review.dto.ResponseDetailCampingInfoReviewDto;
import com.product.application.review.entity.Review;
import com.product.application.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampingService {
    private final CampingRepository campingRepository;
    private final ReviewRepository reviewRepository;
    private final CampingMapper campingMapper;
    public ResponseMessage searchAllCampingInfo(String campingname, String address1, String address2, HttpServletRequest request) {
        List<Camping> campingList;
        List<Camping> returnCampingList = new ArrayList<>();
        // # 검색기능 구현
        /*
            1. 총 8가지 경우로 구분
            <테스트>
                1.1. campingname X address1 X address2 X -> Error 반환 - 0
                1.2. campingname X address1 X address2 O -> Error 반환 - 0
                1.3. campingname X address1 O address2 X -> 검색가능 - 0
                1.4. campingname X address1 O address2 O -> 검색가능 - X
                1.5. campingname O address1 X address2 X -> 검색가능 - 0
                1.6. campingname O address1 X address2 O -> Error 반환 - 0
                1.7. campingname O address1 O address2 X -> 검색가능 - 0
                1.8. campingname O address1 O address2 O -> 검색가능 - 0
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

    public ResponseMessage viewListFive(List<Long> campingIdList) {
        List<ResponseFindListFiveDto> responseFindListFiveDtoList = new ArrayList<>();
        Camping tempCamping;
        for(Long campingId : campingIdList){
            tempCamping = campingRepository.findById(campingId).orElseThrow(()->new CustomException(ErrorCode.CAMPING_NOT_FOUND));
            responseFindListFiveDtoList.add(new ResponseFindListFiveDto(tempCamping));
        }
        return new ResponseMessage("Success",200,responseFindListFiveDtoList);
    }

    public ResponseMessage viewDetailCampingInfo(Long campingId) {
        // 1. 캠핑아이디로 캠핑장 정보를 불러와서 Dto를 만들고 여기에 추가하기
        Camping camping = campingRepository.findById(campingId).orElseThrow(()-> new CustomException(ErrorCode.CAMPING_NOT_FOUND));
        // - ResponseFindDetailCampingInfoDto에 Camping에서 정보를 전달( ReviewList 부분만 누락 )
        ResponseFindDetailCampingInfoDto responseDto = new ResponseFindDetailCampingInfoDto(camping);
        // 2. 캠핑ID로 리뷰 리스트 5개를 찾아서 반환하는 객체에 연결 ( modifiedAt 차순으로 반환 )
        // - reviewRepository에서 시간 내림차순 ( 시간에서의 내림차순 : 늦은 날짜에서 빠른 날짜 순으로 정렬하는 것 -> 1월 4일, 3일, 2일 ... 순)해서 5개만 반환
        // - ResponseDetailCampingInfoReviewDto에 Review를 전달해서 필요한 정보만 저장하고 리스트로 만들어서 ResponseFindDetailCampingInfoDto에 저장
        List<Review> reviewList = reviewRepository.findAllByCamping(camping);
        List<ResponseDetailCampingInfoReviewDto> reviewDtoList = new ArrayList<>();
        for(Review review : reviewList){
            List<String> urlList = review.getReviewUrlList();
            String url;
            // review안에 있는 사진이 0개 일때 null 값을 반환하고 아니라면 첫번째 사진을 반환
            if(urlList.size() == 0) url = null;
            else url = urlList.get(0);
            ResponseDetailCampingInfoReviewDto reviewDto = new ResponseDetailCampingInfoReviewDto(review,url);
            reviewDtoList.add(reviewDto);
        }
        responseDto.updateReviewDtoList(reviewDtoList);
        return new ResponseMessage("Success",200,responseDto);
    }
}
