package com.rhsnc.ovpe.domain;

import lombok.Data;

@Data
public class TbAnlsMe {
    
    // TB_ANLS_ME (분석정보)
    private String chky;    // 검사연도
    private String vnm;     // 차명
    private String vmy;     // 차량연식
    private String vful;    // 연료
    private String engt;    // 엔진형식
    private String rmlg;    // 주행거리 구간
    private String chkm;    // 정밀검사측정방법
    private String vtag;    // 측정값 구분
    private String lim;     // 허용기준
    private String val;     // 측정값 구간
    private String cnt;
    
    // 분석DB 조회용 (테스트)
    private String itemNm;
    private String valset;
}
