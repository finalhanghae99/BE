package com.product.application.camping.controller;

import com.product.application.camping.dto.RequestFindListFiveDto;
import com.product.application.camping.dto.ResponseCampingFiveListDto;
import com.product.application.camping.service.CampingService;
import com.product.application.common.ResponseMessage;
import com.product.application.user.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/camping")
public class CampingController {
    private final CampingService campingService;
    @CrossOrigin(originPatterns = "http://localhost:3000",exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @GetMapping("/search")
    public ResponseMessage searchAllCampingInfo(@RequestParam(value="campingname", required = false) String campingname, @RequestParam(value="address1", required = false) String address1, @RequestParam(value = "address2", required = false) String address2, HttpServletRequest request){
        ResponseMessage responseMessage = campingService.searchAllCampingInfo(campingname, address1, address2, request);
        return responseMessage;
    }
    @CrossOrigin(originPatterns = "http://localhost:3000",exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @PostMapping("/listfive")
    public ResponseMessage viewListFive(@RequestBody RequestFindListFiveDto requestFindListFiveDto){
        List<Long> list = requestFindListFiveDto.getCampingIdList();
        ResponseMessage responseMessage = campingService.viewListFive(list);
        return responseMessage;
    }
    @CrossOrigin(originPatterns = "http://localhost:3000",exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @GetMapping("/{campingId}")
    public ResponseMessage viewDetailCampingInfo(@PathVariable Long campingId, HttpServletRequest request){
        ResponseMessage responseMessage = campingService.viewDetailCampingInfo(campingId, request);
        return responseMessage;
    }
    @CrossOrigin(originPatterns = "http://localhost:3000",exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @PostMapping("/{campingId}/like")
    public ResponseMessage updateCampingLikeState(@PathVariable Long campingId, HttpServletRequest request){
        return campingService.updateCampingLikeState(campingId, request);
    }

    @CrossOrigin(originPatterns = "http://localhost:3000",exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @GetMapping("/bestfive")
    public ResponseMessage<?> searchLikeFive(){
        ResponseCampingFiveListDto responseCampingFiveListDto = campingService.searchLikeFive();
        return new ResponseMessage<>("Success", 200, responseCampingFiveListDto);
    }
}
