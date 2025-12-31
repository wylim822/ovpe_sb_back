package com.rhsnc.ovpe.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rhsnc.ovpe.domain.CegCarMig;
import com.rhsnc.ovpe.domain.TbEetHisMe;
import com.rhsnc.ovpe.mapper.VhclAnlsMapper;
import com.rhsnc.ovpe.service.VhclAnlsService;

@Service
public class VhclAnlsServiceImpl implements VhclAnlsService{
    
    private final VhclAnlsMapper vhclAnlsMapper;

    public VhclAnlsServiceImpl(VhclAnlsMapper vhclAnlsMapper) {
        this.vhclAnlsMapper = vhclAnlsMapper;
    }

    // API KEY 값 (application.properties)
    @Value("${openai.api.key}")
    private String apiKey;
    
    // 등록정보 조회
    @Override
    public CegCarMig getVhclInfo(String carRegNo){
        return vhclAnlsMapper.selectVhclInfo(carRegNo);
    }

    // 검사정보 목록 조회
    @Override
    public List<TbEetHisMe> getInspInfoList(String carRegNo) {
        return vhclAnlsMapper.selectInspInfoList(carRegNo);
    }


    // 분석DB 조회 및 ChatGPT API 호출
    @Override
    public Map<String, Object> getVhclAnlsInfo(Map<String, Object> vhclInfo) {
        /* 
            !!차량번호 최초 조회인 경우, 조회결과 DB 저장 로직 작성 필요
            !!추후 화면에서 최종 API 호출결과만 보여주도록 수정 필요
        */
        Map<String, Object> apiRslt = new HashMap<>();  // 최종 API 호출 결과

        // ChatGPT API 호출 메시지 셋팅
        // 01. role : user (검사데이터를 기반으로 분석DB 조회)
        String vhrno = (String) vhclInfo.get("vhrno");
        String fuel = (String) vhclInfo.get("fuel");

        // 01-1. 분석DB 조회 시 필요한 변수 조회
        Map<String, Object> anlsVars = getLatestInspVarsByFuel(fuel, vhrno); 

        // 01-2. 분석DB 조회
        String userMsg = getAnlsRptByFuel(fuel, anlsVars);

        // 02. role : system
        String systemMsg = vhclAnlsMapper.selectApiSystemText(anlsVars);

        // ChatGPT API 호출 --> !!추후 바로 연결
        String anlsMsg = "";
        if (systemMsg != null && !systemMsg.isEmpty() && userMsg != null && !userMsg.isEmpty()) {
            //anlsMsg = callAnlsApi(systemMsg, userMsg);
        }

        apiRslt.put("systemMsg", systemMsg);
        apiRslt.put("userMsg", userMsg);
        apiRslt.put("anlsMsg", anlsMsg);
        
        return apiRslt;
    }

    // 연료별 분석DB 조회용 변수 조회 (최신 검사정보)
    private Map<String, Object> getLatestInspVarsByFuel(String fuel, String vhrno) {

        if (fuel != null && fuel.contains("휘발유")) {
            return vhclAnlsMapper.selectLatestInspVarsG(vhrno);

        } else if (fuel != null && fuel.contains("경유")) {
            return vhclAnlsMapper.selectLatestInspVarsD(vhrno);

        }

        return null; // LPG, 전기 etc (!!추후 연료 추가 시 수정)
    }

    // 연료별 분석DB 조회
    private String getAnlsRptByFuel(String fuel, Map<String, Object> anlsVars) {
        List<String> anlsMeList = new ArrayList<>();
        String addDrctn = ""; // 추가 지시문구

        if (anlsVars == null || anlsVars.get("V_CHKM") == null) {
            return null;
        }else {
            addDrctn = anlsVars.get("V_CHKM") + " 기준으로 차자바 프리미엄 리포트 형식으로 작성해 주세요.";
        }

        if (fuel != null && fuel.contains("휘발유")) {
            anlsMeList = vhclAnlsMapper.selectAnlsRptAllG(anlsVars);
            anlsMeList.add(addDrctn);
        } else if (fuel != null && fuel.contains("경유")) {
            anlsMeList = vhclAnlsMapper.selectAnlsRptAllD(anlsVars);
            anlsMeList.add(addDrctn);
        } else {
            return null;
        }

        // 조회 결과 문자열 변환 (userMsg)
        return anlsMeList.isEmpty() ? "" : String.join("\n", anlsMeList);
    }

    // 분석API 호출 (chatGPT)
    private String callAnlsApi(String systemMsg, String userMsg){
        String url = "https://api.openai.com/v1/chat/completions";

        // 1. 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // 2. 바디 데이터 구성 (Map 활용)
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");
        
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", systemMsg));
        messages.add(Map.of("role", "user", "content", userMsg));
        
        requestBody.put("messages", messages);

        // 3. 호출 시도
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                // OpenAI 응답 JSON 구조 파싱
                //System.out.println("@@getBody: " + response.getBody());
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                
                return (String) message.get("content");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "API 호출 중 오류가 발생했습니다: " + e.getMessage();
        }

        return "API 결과를 받지 못했습니다.";
    }

    // API 결과 파싱
    private Map<String, Object> parseApiRslt(String apiRsltTxt){
        apiRsltTxt = apiRsltTxt.replace("```json", "").replace("```", "").trim();

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(apiRsltTxt, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            throw new RuntimeException("GPT 응답 JSON 파싱 실패", e);
        }
    }

    // 항목별 분리
    private Map<String, Object> extrJsonData(Map<String, Object> parsed){
        Map<String, Object> rslt = new LinkedHashMap<>();

        rslt.put("ovrlEvl", parsed.get("종합평가"));
        rslt.put("ovrlGrd", parsed.get("종합등급"));
        rslt.put("expcChg", parsed.get("향후 5년 운행 시 예상 변화"));
        rslt.put("mntn", parsed.get("정비 권장사항"));
        rslt.put("emisAnls", parsed.get("배출가스 분석"));
        rslt.put("ageExpln", parsed.get("실질연식 및 연식이득"));
        rslt.put("graphExpln", parsed.get("방사형 그래프 해석"));
        rslt.put("rmnVal", parsed.get("잔존가치 해석"));
        rslt.put("notice", parsed.get("중요 고지문"));

        return rslt;
    }

    // API호출 테스트 (!!추후 정리 필요)
    @Override
    public Map<String, Object> getCallApiRslt(Map<String, Object> req) {
        String systemMsg = (String)req.get("systemMsg");
        String userMsg = (String)req.get("userMsg");
        
        // ChatGPT API 호출
        String apiRsltTxt = callAnlsApi(systemMsg, userMsg);

        // 호출결과 JSON 파싱
        Map<String, Object> parsed = parseApiRslt(apiRsltTxt);
        
        // 화면 리턴용 데이터 변환
        Map<String, Object> apiRslt = extrJsonData(parsed);

        return apiRslt;
    }
}
