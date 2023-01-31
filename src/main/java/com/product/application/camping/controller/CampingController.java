package com.product.application.camping.controller;

import com.product.application.camping.dto.RequestFindListFiveDto;
import com.product.application.camping.dto.ResponseCampingFiveListDto;
import com.product.application.camping.service.CampingService;
import com.product.application.common.ResponseMessage;
import com.product.application.review.dto.RequestFindListTenDto;
import com.product.application.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/camping")
public class CampingController {
    private final CampingService campingService;

    @GetMapping("/permit/search")
    public ResponseMessage searchAllCampingInfo(@RequestParam(value="campingname", required = false) String campingname, @RequestParam(value="address1", required = false) String address1, @RequestParam(value = "address2", required = false) String address2, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long usersId;
        if(userDetails != null){
            usersId = userDetails.getUserId();
        }
        usersId = 0L;
        ResponseMessage responseMessage = campingService.searchAllCampingInfo(campingname, address1, address2, usersId);
        return responseMessage;
    }

    @PostMapping("/permit/listfive")
    public ResponseMessage viewListFive(@RequestBody RequestFindListFiveDto requestFindListFiveDto){
        List<Long> list = requestFindListFiveDto.getCampingIdList();
        ResponseMessage responseMessage = campingService.viewListFive(list);
        return responseMessage;
    }

    @PostMapping("/permit/listten")
    public ResponseMessage findListTen(@RequestBody RequestFindListTenDto requestFindListTenDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long usersId;
        if(userDetails != null){
            usersId = userDetails.getUserId();
        }
        usersId = 0L;

        List<Long> list = requestFindListTenDto.getCampingIdList();
        return campingService.findListTen(list,usersId);
    }

    @GetMapping("/permit/{campingId}")
    public ResponseMessage viewDetailCampingInfo(@PathVariable Long campingId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long usersId;
        if(userDetails != null){
            usersId = userDetails.getUserId();
        } else {
            usersId = 0L;
        }

        ResponseMessage responseMessage = campingService.viewDetailCampingInfo(campingId, usersId);
        return responseMessage;
    }

    @PostMapping("{campingId}/like")
    public ResponseMessage updateCampingLikeState(@PathVariable Long campingId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long usersId = userDetails.getUserId();
        return campingService.updateCampingLikeState(campingId, usersId);
    }


    @GetMapping("/permit/bestfive")
    public ResponseMessage<?> searchLikeFive(){
        ResponseCampingFiveListDto responseCampingFiveListDto = campingService.searchLikeFive();
        return new ResponseMessage<>("Success", 200, responseCampingFiveListDto);
    }


}
