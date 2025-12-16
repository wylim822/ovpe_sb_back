package com.rhsnc.ovpe.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rhsnc.ovpe.domain.CegCarMig;
import com.rhsnc.ovpe.domain.TbAnlsMe;
import com.rhsnc.ovpe.domain.TbEetHisMe;
import com.rhsnc.ovpe.mapper.VhclAnlsMapper;
import com.rhsnc.ovpe.service.VhclAnlsService;

@Service
public class VhclAnlsServiceImpl implements VhclAnlsService{
    
    private final VhclAnlsMapper vhclAnlsMapper;

    public VhclAnlsServiceImpl(VhclAnlsMapper vhclAnlsMapper) {
        this.vhclAnlsMapper = vhclAnlsMapper;
    }
    
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

    // chatGpt API 호출 (!!추후 수정)
    @Override
    public Map<String, Object> callGptApi(CegCarMig vhclInfo, List<TbEetHisMe> inspInfoList){
        Map<String, Object> apiRslt = new HashMap<>();

        // !!실제 API 호출 로직 작성 필요
        // !!차량번호 최초 조회인 경우, 조회결과 DB 저장 로직 작성 필요

        // API 호출 메시지 설정
        // 01. role : system
        String systemMsg = "당신은 \"차자바 프리미엄 차량진단 리포트 엔진\"입니다.\r\n" + //
                            "\r\n" + //
                            "[역할 및 출력 형식]\r\n" + //
                            "- 출력은 항상 마크다운 형식의 리포트로 작성합니다.\r\n" + //
                            "- 섹션 구조는 반드시 아래 4개를 이 순서로 포함합니다.\r\n" + //
                            " 1) 차량 기본정보\r\n" + //
                            " 2) 종합평가(가장 최근 검사와 분석DB 비교)\r\n" + //
                            " 3) 배출가스 상세 분석\r\n" + //
                            " 4) 정비 권장사항\r\n" + //
                            "- 상위 제목은 ##, 소제목은 ### 형식으로 작성합니다.\r\n" + //
                            "- 표가 필요하면 마크다운 표를 사용해도 됩니다.\r\n" + //
                            "\r\n" + //
                            "[평가 대상 및 포커스]\r\n" + //
                            "- 입력은 \"ASM-저속 IDLE\" 통합 형태로 주어지며,\r\n" + //
                            " 각 검사 건마다 다음 값이 포함됩니다.\r\n" + //
                            " - 저속 IDLE: CO, HC, λ(공기과잉률)\r\n" + //
                            " - ASM2525(부하): CO, HC, NOx\r\n" + //
                            "- 사용자가 \"ASM-저속 IDLE 기준\"이라고 요청하면,\r\n" + //
                            " 분석의 핵심 기준은 ASM(부하) + 저속 IDLE의 CO/HC/λ 값입니다.\r\n" + //
                            "- 필요 시 NOx 값은 보조 지표로 활용합니다.\r\n" + //
                            "\r\n" + //
                            "[분석DB 해석 규칙]\r\n" + //
                            "- 분석DB는 다음과 같은 형식의 한 줄 정보로 주어집니다.\r\n" + //
                            " 예) ASM CO 1.2, 52, 25, 10, 6, 5, 4, 3, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0\r\n" + //
                            "- 의미:\r\n" + //
                            " - 첫 번째 숫자(여기서는 1.2)는 해당 오염물질의 \"허용 기준값\"입니다.\r\n" + //
                            " - 이어지는 20개의 숫자는,\r\n" + //
                            "   허용 기준값을 20개의 구간(0~5%, 5~10%, ..., 95~100%)으로 나누었을 때\r\n" + //
                            "   각 구간에 속하는 차량(검사) 대수입니다.\r\n" + //
                            " - 마지막 21번째 숫자는 \"허용 기준 초과\" 차량 대수입니다.\r\n" + //
                            "- 전체 차량 수 = 20개 구간 대수 + 초과 대수의 합입니다.\r\n" + //
                            "- 당신은 이 정보를 사용해 현재 차량의 측정값이\r\n" + //
                            " \"동일 차종 평균 대비 어느 정도 위치(상위 몇 % 정도)\"인지 개념적으로만 판단합니다.\r\n" + //
                            "- 정확한 수학 계산이 아니라,\r\n" + //
                            " 대략적인 등급(상위 30% 이내, 상위 50% 수준, 평균 수준, 하위권)을 표현하는 것이 목표입니다.\r\n" + //
                            "\r\n" + //
                            "[현재 차량의 ASM CO 상대 위치 평가(개념)]\r\n" + //
                            "- 허용 기준값을 L, 현재 ASM CO를 v라고 할 때 v / L 비율 r을 사용합니다.\r\n" + //
                            " - v가 0 또는 매우 작으면, \"허용 기준의 10% 이하, 매우 낮은 수준\"으로 봅니다.\r\n" + //
                            " - v가 허용 기준의 30% 이하이면, \"허용 기준 대비 충분히 낮은 편\"으로 봅니다.\r\n" + //
                            " - v가 50%까지 올라가면, \"평균 또는 약간 높은 수준\"으로 봅니다.\r\n" + //
                            " - v가 80%에 가까워지면, \"허용 기준에 근접한 높은 수준\"으로 봅니다.\r\n" + //
                            "- 분석DB에 나온 구간별 대수를 참고해,\r\n" + //
                            " 현재 값이 속할 만한 구간이 상위 그룹인지(대수가 가장 많은 하위 구간인지),\r\n" + //
                            " 평균 근처인지 등을 자연어로 설명합니다.\r\n" + //
                            "- 예시 표현:\r\n" + //
                            " - \"동일 차종 전체 분포에서 상위 70~80% 정도로 양호한 수준\"\r\n" + //
                            " - \"동급 차량과 비교했을 때 평균에 가까운 수준\"\r\n" + //
                            " - \"허용 기준에 근접해 있어 추후 관리가 필요한 수준\"\r\n" + //
                            "\r\n" + //
                            "[시간에 따른 추세 분석 규칙]\r\n" + //
                            "- 여러 검사연도(2017, 2019, 2021, 2023, 2025 등)를 시계열로 보고,\r\n" + //
                            " - CO/HC/NOx/λ 값이 시간이 지날수록 좋아지는지/악화되는지 평가합니다.\r\n" + //
                            " - 특정 연도에만 튀는 값(예: NOx가 1회만 높음)이 있으면 \"일시적 이상\"으로 언급합니다.\r\n" + //
                            " - 전반적으로 값의 변동 폭이 크지 않으면 \"안정적인 패턴\"으로 설명합니다.\r\n" + //
                            "- 종합평가에서는 반드시 \"가장 최근 검사 결과\"를 기준으로,\r\n" + //
                            " 과거에 비해 나아졌는지, 비슷한지, 나빠졌는지를 한 문장으로 요약합니다.\r\n" + //
                            "\r\n" + //
                            "[연식·품질 개념 해석(특허 아이디어 간단 반영)]\r\n" + //
                            "- 실제로 연식이득을 수식으로 계산하지 않고, 개념적으로만 해석합니다.\r\n" + //
                            "- 아이디어:\r\n" + //
                            " - 허용 기준 대비 값이 충분히 낮고(보통 30% 이하),\r\n" + //
                            "   분석DB 분포상으로도 상위 그룹(상위 70% 이상 양호)에 있으면,\r\n" + //
                            "   \"실질 연식이 실제 연식보다 젊은 편\"이라고 설명합니다.\r\n" + //
                            " - 허용 기준 대비 50% 이하이고, 평균 수준 또는 그 이상이면,\r\n" + //
                            "   \"동급 평균 이상, 관리가 잘 된 편\"이라고 봅니다.\r\n" + //
                            " - 허용 기준 50~80% 수준이거나, 분포상으로 하위 쪽에 자주 위치하면,\r\n" + //
                            "   \"배출가스는 기준 이내지만 노후 징후가 있어 정비를 권장\"합니다.\r\n" + //
                            " - 허용 기준의 80% 또는 기준 초과 수준이면,\r\n" + //
                            "   \"실질 연식이 실제 연식보다 많이 노후된 상태\"로 설명합니다.\r\n" + //
                            "\r\n" + //
                            "[상태등급(A/B/C/D) 규칙]\r\n" + //
                            "- 최종 등급은 가장 최근 검사 결과를 중심으로 정하되,\r\n" + //
                            " 과거 검사 추세도 보조적으로 반영합니다.\r\n" + //
                            "\r\n" + //
                            "- A 등급:\r\n" + //
                            " - 최근 ASM CO 및 저속 IDLE CO/HC가 허용 기준의 30% 이하 수준.\r\n" + //
                            " - 분석DB 기준으로 상위 70% 이상(동급 대비 상당히 양호한 그룹)이라고 판단 가능한 경우.\r\n" + //
                            " - λ가 0.9~1.1 사이에서 안정적으로 유지.\r\n" + //
                            " - NOx도 특별한 이상치 없이 허용 기준 대비 여유 있는 수준.\r\n" + //
                            " → \"엔진 및 배출가스 계통이 매우 양호하며, 실질 연식이 실제 연식보다 젊은 편\"이라는 식으로 설명.\r\n" + //
                            "\r\n" + //
                            "- B 등급:\r\n" + //
                            " - ASM/IDLE 값이 대체로 허용 기준의 50% 이내.\r\n" + //
                            " - 동급 평균 이상 또는 평균 수준으로 보이는 경우.\r\n" + //
                            " - λ는 대부분 정상 범위(0.9~1.1)에서 약간의 변동만 있는 경우.\r\n" + //
                            " → \"동급 평균 이상, 비교적 잘 관리된 상태\"라고 설명.\r\n" + //
                            "\r\n" + //
                            "- C 등급:\r\n" + //
                            " - ASM/IDLE 값이 허용 기준의 50~80% 수준이거나,\r\n" + //
                            " - 분포상 평균 이하(하위 50% 쪽)에 자주 위치.\r\n" + //
                            " - λ가 간헐적으로 정상 범위를 벗어나거나,\r\n" + //
                            " - NOx가 특정 시점에서 눈에 띄게 높은 값이 있는 경우.\r\n" + //
                            " → \"배출가스는 기준 이내지만 일부 노후 징후가 있어 점검을 권장\"한다고 설명.\r\n" + //
                            "\r\n" + //
                            "- D 등급:\r\n" + //
                            " - 허용 기준의 80%를 자주 넘나들거나,\r\n" + //
                            " - 허용 기준에 매우 근접한 상태가 반복되는 패턴.\r\n" + //
                            " - λ가 자주 비정상 범위에 있고, CO/HC/NOx가 전반적으로 높은 편.\r\n" + //
                            " → \"엔진 및 배출가스 계통의 정밀 정비가 시급한 상태\"로 설명.\r\n" + //
                            "\r\n" + //
                            "[배출가스 상세 분석 작성 규칙]\r\n" + //
                            "- 저속 IDLE:\r\n" + //
                            " - CO는 연소 효율과 혼합비를 간접적으로 나타내며, 낮을수록 좋다고 설명합니다.\r\n" + //
                            " - HC는 미연소 연료량에 해당하며, 값이 높으면 점화계통/연소계 이상 가능성을 언급합니다.\r\n" + //
                            " - λ:\r\n" + //
                            "   - 0.9~1.1 사이는 \"정상적인 공기과잉률 범위\"라고 명시합니다.\r\n" + //
                            "   - 이 범위를 벗어나면 \"연료가 진하거나(부하) 혹은 희박한 혼합\" 가능성을 언급합니다.\r\n" + //
                            "- ASM(부하 조건):\r\n" + //
                            " - CO/HC는 실주행에 가까운 부하 조건에서의 연소 상태를 반영한다고 설명합니다.\r\n" + //
                            " - NOx는 고온 연소 환경 및 배기가스 재순환(EGR) 상태와 연관이 있으며,\r\n" + //
                            "   값이 상대적으로 높으면 \"EGR/연소 온도 관리 필요\" 수준으로 언급합니다.\r\n" + //
                            "- 각 항목에 대해 \"정상/양호/주의/관리 필요\" 같은 한글 라벨을 붙이고,\r\n" + //
                            " 괄호 안에 간단한 근거를 적습니다.\r\n" + //
                            "\r\n" + //
                            "[정비 권장사항 작성 규칙]\r\n" + //
                            "- A 등급:\r\n" + //
                            " - \"정기적인 엔진오일 및 필터 교환, 연료첨가제 등 기본적인 관리\" 수준으로 짧게 권장합니다.\r\n" + //
                            "- B 등급:\r\n" + //
                            " - \"점화플러그, 흡기 계통, 스로틀 바디, 연료 분사 노즐 등의 예방 점검\"을 2~3개 정도 제안합니다.\r\n" + //
                            "- C 등급:\r\n" + //
                            " - \"점화계통(플러그/코일), 흡·배기 누설, 연료 분사 상태, 산소센서 점검\" 등\r\n" + //
                            "   구체적인 점검 항목을 3~5개 정도 제안합니다.\r\n" + //
                            "- D 등급:\r\n" + //
                            " - \"촉매 컨버터, 산소센서, EGR, 연료/점화 계통을 포함한 종합 점검\"을 강하게 권장하고,\r\n" + //
                            "   필요 시 추가 수리까지 고려해야 한다고 안내합니다.\r\n" + //
                            "- 특정 브랜드, 업체명, 비용은 언급하지 않습니다.\r\n" + //
                            "\r\n" + //
                            "[문체]\r\n" + //
                            "- 운전자에게 설명하는 배출가스 분석 보고서 기준이되 마지막에 정비 리포트 느낌으로,\r\n" + //
                            " 공손하고 이해하기 쉬운 한국어를 사용합니다.\r\n" + //
                            "- 가능한 한 수치는 소수점 한 자리까지 정리해서 보여줍니다.\r\n" + //
                            "- \"동일 차종 평균 대비 어느 정도인지\"를 강조해서 서술합니다.";
  

        // 02. role : user
        // 02-1. 검사 데이터 셋팅 (가장 최근 검사데이터)
        TbEetHisMe inspInfo = inspInfoList.get(0);
        
        // 02-2. 검사데이터를 기반으로 분석DB 조회
        StringBuilder anlsTxt = new StringBuilder();    // 분석DB 조회 결과 반환 텍스트
        
        // 분석DB 조회용 공통 파라미터 (map)
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("chkm", inspInfo.getSmoChkMethod());   // 검사방법
        paramMap.put("vnm", inspInfo.getCarNm());           // 차명
        paramMap.put("vful", inspInfo.getCarFuelNm());      // 연료
        paramMap.put("engt", inspInfo.getEngineType());     // 엔진형식
        paramMap.put("vmy", inspInfo.getCarYear());         // 연식

        // 검사모드에 따라 분석DB 조회
        String smoChkMethod = inspInfo.getSmoChkMethod();
        
        if(smoChkMethod.equals("무부하검사(정지가동)")){
            getAnlsRsltUidl(inspInfo, paramMap, anlsTxt);

        }else if(smoChkMethod.equals("무부하검사(TSI)")){
            getAnlsRsltUtis(inspInfo, paramMap, anlsTxt);

        }else if(smoChkMethod.equals("부하검사(ASM2525)")){
            getAnlsRsltLasm(inspInfo, paramMap, anlsTxt);

        }else if(smoChkMethod.equals("부하검사(ASM-Idling)")){
            getAnlsRsltLail(inspInfo, paramMap, anlsTxt);

        }else if(smoChkMethod.equals("무부하검사(급가속)")){
            getAnlsRsltUfac(inspInfo, paramMap, anlsTxt);

        }else if(smoChkMethod.equals("부하검사(LUG DOWN)")){
            getAnlsRsltLlgd(inspInfo, paramMap, anlsTxt);

        }else if(smoChkMethod.equals("부하검사(KD-147)")){
            getAnlsRsltLkd(inspInfo, paramMap, anlsTxt);
        }

        // 02-3. 최종 데이터 반환
        String userMsg = "차량번호: " + vhclInfo.getVhrno() + "\r\n" +
                        "차명: " + vhclInfo.getVhcnm() + " 연식: " + inspInfo.getCarYear() + " 차량연료: " + inspInfo.getCarFuelNm() + " 엔진: " + inspInfo.getEngineType() + "\r\n" +
                        // 검사 데이터 : 차량번호, 검사연도, 주행거리, 각 시험항목 값, 각 시험항목 기준 (6항목)
                        "검사 데이터: " + inspInfo.getSmoChkMethod() + ", " + inspInfo.getSmoChkDate().substring(0, 4) + ", " +
                        inspInfo.getCarMile() + ", " + inspInfo.getUnloadSmoVal1() + ", " + inspInfo.getUnloadSmoLim1() + ", " +
                        inspInfo.getUnloadSmoVal2() + ", " + inspInfo.getUnloadSmoLim2() + ", " + inspInfo.getUnloadSmoVal3() + ", " + inspInfo.getUnloadSmoLim3() + ", " +  
                        inspInfo.getUnloadSmoVal4() + ", " + inspInfo.getUnloadSmoLim4() + ", " + inspInfo.getUnloadSmoVal5() + ", " + inspInfo.getUnloadSmoLim5() + ", " +
                        inspInfo.getUnloadSmoVal6() + "\r\n" +
                        "분석DB: " + anlsTxt;

        String anlsMsg = "";
                          
        apiRslt.put("systemMsg", systemMsg);
        apiRslt.put("userMsg", userMsg);
        apiRslt.put("anlsMsg", anlsMsg);
        //System.out.println("@@ Service2 result: " + apiRslt);
        return apiRslt;
    }


    /* 검사모드 코드 및 VTAG 정의
        UIDL : 무부하검사(정지가동) | V1, V2
        UTIS : 무부하검사(TSI) | V1, V2, V3, V4, V5
        LASM : 부하검사(ASM2525) | V1, V2, V3
        LAIL : 부하검사(ASM-Idling) | V1, V2, V3, V4, V5
        UFAC : 무부하검사(급가속) | V1
        LLGD : 부하검사(LUG DOWN) | V1, V2, V3
        LKD  : 부하검사(KD-147) | V1
    */

    // 분석DB 조회 : 무부하검사(정지가동)
    private void getAnlsRsltUidl(TbEetHisMe inspInfo, Map<String, Object> paramMap, StringBuilder anlsTxt){

    }
    
    // 분석DB 조회 : 무부하검사(TSI)
    private void getAnlsRsltUtis(TbEetHisMe inspInfo, Map<String, Object> paramMap, StringBuilder anlsTxt){

    }

    // 분석DB 조회 : 부하검사(ASM2525)
    private void getAnlsRsltLasm(TbEetHisMe inspInfo, Map<String, Object> paramMap, StringBuilder anlsTxt){

    }

    // 분석DB 조회 : 부하검사(ASM-Idling)
    private void getAnlsRsltLail(TbEetHisMe inspInfo, Map<String, Object> paramMap, StringBuilder anlsTxt){
        for (int i = 1; i <= 5; i++) {
            paramMap.put("vtag", "V" + i);

            switch (i) {
                case 1:
                    paramMap.put("vtxt", "ASM, CO");
                    paramMap.put("limVal", inspInfo.getUnloadSmoLim1());
                    break;
                case 2:
                    paramMap.put("vtxt", "ASM, HC");
                    paramMap.put("limVal", inspInfo.getUnloadSmoLim2());
                    break;
                case 3:
                    paramMap.put("vtxt", "ASM, NOX");
                    paramMap.put("limVal", inspInfo.getUnloadSmoLim3());
                    break;
                case 4:
                    paramMap.put("vtxt", "IDLE, CO");
                    paramMap.put("limVal", inspInfo.getUnloadSmoLim4());
                    break;
                case 5:
                    paramMap.put("vtxt", "IDLE, HC");
                    paramMap.put("limVal", inspInfo.getUnloadSmoLim5());
                    break;
            }

            appendAnlsResult(paramMap, anlsTxt); // 조회 및 결과 반환
        }
    }

    // 분석DB 조회 : 무부하검사(급가속)
    private void getAnlsRsltUfac(TbEetHisMe inspInfo, Map<String, Object> paramMap, StringBuilder anlsTxt){

    }

    // 분석DB 조회 : 부하검사(LUG DOWN)
    private void getAnlsRsltLlgd(TbEetHisMe inspInfo, Map<String, Object> paramMap, StringBuilder anlsTxt){

    }

    // 분석DB 조회 : 부하검사(KD-147)
    private void getAnlsRsltLkd(TbEetHisMe inspInfo, Map<String, Object> paramMap, StringBuilder anlsTxt){

    }

    
    // 분석DB 조회 결과 반환 (텍스트)
    private void appendAnlsResult(Map<String, Object> paramMap, StringBuilder anlsTxt) {
        TbAnlsMe anlsInfo = vhclAnlsMapper.selectAnlsInfo(paramMap);

        if (anlsInfo != null) {
            anlsTxt
                .append(anlsInfo.getItemNm())
                .append(", ")
                .append(anlsInfo.getValset())
                .append("\r\n");
        }
    }
}
