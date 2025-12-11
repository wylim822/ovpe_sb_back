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

    @PostMapping("/anlsCarRegNo")
    public Map<String, Object> vhclAnls(@RequestBody Map<String, String> request){
        String carRegNo = request.get("carRegNo");

        Map<String, Object> rslt = new HashMap<>();
        List<TbEetHisMe> inspInfoList = new ArrayList<>();  // 검사정보 목록 조회
        Map<String, Object> apiInfo = new HashMap<>();      // API 호출 결과

        // 01. 등록정보 조회
        CegCarMig vhclInfo = vhclAnlsService.getVhclInfo(carRegNo);
        System.out.println("@@vhclInfo: " + vhclInfo);

        if(vhclInfo != null){
            // 02. 검사정보 목록 조회 (INDIVIDUAL_INSPECTION)
            inspInfoList = vhclAnlsService.getInspInfoList(carRegNo);

            // 03. chatGPT API 호출 - !!추후 수정
            apiInfo = vhclAnlsService.callGptApi(vhclInfo);
        }
        
        // 최종 리턴 값 지정
        rslt.put("vhclInfo", vhclInfo);
        rslt.put("inspInfoList", inspInfoList); 
        rslt.put("apiInfo", apiInfo);

        System.out.println("@@최종 rslt: " + rslt);
        return rslt; 
    }
}
