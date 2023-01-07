package com.product.application.openapi.service;

import com.product.application.camping.entity.Camping;
import com.product.application.camping.repository.CampingRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final CampingRepository campingRepository;

    public void init(String jsonData) {
        try {
            //Json 객체를 생성.
            System.out.println("들어왔다 = " );

            //Json 파싱 객체를 생성.
            JSONParser jsonParser = new JSONParser();
            //파싱할 String(Controller에서 호출해 값이 저장된 StringBuilder result)을
            //Json 객체로 파서를 통해 저장.
            JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonData);


            //데이터 분해 단계

            //response를 받아옴
            JSONObject parseResponse = (JSONObject) jsonObj.get("response");
            //parseResponse에는 response 내부의 데이터들이 들어있음

            //body를 받아옴
            JSONObject parseBody = (JSONObject) parseResponse.get("body");
            //parseBody에는 body 내부의 데이터들이 들어 있음.

            //items 안쪽의 데이터는 [] 즉 배열의 형태이기에 제이슨 배열로 받아온다.
            JSONObject items = (JSONObject) parseBody.get("items");

            JSONArray array = (JSONArray) items.get("item");

            //생성해놓은 도메인에 매핑
            for (int i = 0; i < array.size(); i++) {
                JSONObject jObj2 = (JSONObject)array.get(i);

                Camping camping = new Camping(jObj2.get("facltNm").toString(),jObj2.get("doNm").toString(),jObj2.get("sigunguNm").toString(),jObj2.get("addr1").toString(),jObj2.get("lctCl").toString(),
                                                jObj2.get("sbrsCl").toString(), jObj2.get("mapX").toString(), jObj2.get("mapY").toString(), jObj2.get("induty").toString(),jObj2.get("homepage").toString(),
                        jObj2.get("tel").toString(),jObj2.get("posblFcltyCl").toString(), jObj2.get("firstImageUrl").toString(),0L,0L );
                campingRepository.save(camping);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
