package com.product.application.camping.controller;

import com.product.application.camping.service.CampingService;
import com.product.application.common.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/camping")
public class CampingController {
    private final CampingService campingService;
    @GetMapping("search")
    public ResponseMessage searchAllCampingInfo(@RequestParam(value="campingname", required = false) String campingname, @RequestParam(value="address1", required = false) String address1, @RequestParam(value = "address2", required = false) String address2, HttpServletRequest request){
        ResponseMessage responseMessage = campingService.searchAllCampingInfo(campingname, address1, address2, request);
        return responseMessage;
    }
}
