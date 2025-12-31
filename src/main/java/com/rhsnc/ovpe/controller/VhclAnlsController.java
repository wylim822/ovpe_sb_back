package com.rhsnc.ovpe.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhsnc.ovpe.domain.CegCarMig;
import com.rhsnc.ovpe.domain.TbEetHisMe;
import com.rhsnc.ovpe.service.VhclAnlsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vhcl")
public class VhclAnlsController {
    private final VhclAnlsService vhclAnlsService;

    // 차량 기본정보 조회 (등록정보, 검사정보)
    @PostMapping("/vhclBscInfo")
    public Map<String, Object> vhclBasicInfo(@RequestBody Map<String, String> request) {
        String carRegNo = request.get("carRegNo");

        Map<String, Object> rslt = new HashMap<>();
        List<TbEetHisMe> inspInfoList = new ArrayList<>();  // 검사정보 목록 조회

        // 01. 등록정보 조회
        CegCarMig vhclInfo = vhclAnlsService.getVhclInfo(carRegNo);

        if(vhclInfo != null){
            // 02. 검사정보 목록 조회 (INDIVIDUAL_INSPECTION)
            inspInfoList = vhclAnlsService.getInspInfoList(carRegNo);
        }
        
        // 최종 리턴 값 지정
        rslt.put("vhclInfo", vhclInfo);
        rslt.put("inspInfoList", inspInfoList); 

        return rslt; 
    }

    // 차량 분석정보 조회 및 ChatGPT API 호출
    @PostMapping("/vhclAnlsInfo")
    public Map<String, Object> vhclAnlsInfo(@RequestBody Map<String, Object> vhclInfo) {
    //public ResponseEntity<?> vhclAnlsInfo(@RequestBody Map<String, Object> vhclInfo) {
        Map<String, Object> apiInfo = new HashMap<>();
        apiInfo = vhclAnlsService.getVhclAnlsInfo(vhclInfo);    // 분석DB 조회 및 ChatGPT API 호출

        // if(apiInfo == null) {
        //     return ResponseEntity.ok().body(null);
        // }

        // return ResponseEntity.ok(apiInfo);

        return apiInfo;
    }

    // API 호출 테스트 (!!추후 정리 필요)
    @PostMapping("/callApiTest")
    public Map<String, Object> callApiTest(@RequestBody Map<String, Object> req) {
        Map<String, Object> callRslt = new HashMap<>();
        //String apiRslt = vhclAnlsService.getCallApiRslt(req);
        //callRslt.put("anlsMsg", apiRslt);
        callRslt = vhclAnlsService.getCallApiRslt(req);

        //System.out.println("@@최종결과: " + callRslt);
        return callRslt;
    }
}
