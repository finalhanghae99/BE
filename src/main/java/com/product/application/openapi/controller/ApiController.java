package com.product.application.openapi.controller;

import com.product.application.openapi.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequiredArgsConstructor
public class ApiController {
    private final ApiService apiService;

    @GetMapping("/api/test")
    public void getApi() throws IOException {
        StringBuilder result = new StringBuilder();

        String urlStr ="http://apis.data.go.kr/B551011/GoCamping/basedList?" +                  //http://apis.data.go.kr/B551011/GoCamping는 endpoint주소 , /basedList는 내가 사용하는 서비스
                "ServiceKey=5dTujJ6wogWD2Ihr77xVuJpvpgPqARMFWrfhyt4lrlGJhl5wx57ZHDnxsJdNV4Q7AkhKIPX7flh59yeiLcaVMg==" +     //Servicekey는 내가 받은 키 중 디코딩키를 사용( 포스트맨에서는 인코딩키 사용)
                //"&pageNo=1" +
                "&numOfRows=3500" +
                "&MobileOS=ETC" +
                "&MobileApp=AppTest" +
                "&_type=json";
        URL url = new URL(urlStr);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

        String returnLine;

        while((returnLine = br.readLine()) != null){
            result.append(returnLine+"\n\r");
        }
        urlConnection.disconnect();
        apiService.init(result.toString());
    }
}
