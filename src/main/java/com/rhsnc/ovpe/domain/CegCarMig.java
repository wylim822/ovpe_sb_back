package com.rhsnc.ovpe.domain;

import lombok.Data;

@Data
public class CegCarMig {
    // CEG_CAR_MIG (등록정보)
    private String vhmno;               // 차량관리번호
    private String vhrno;               // 차량등록번호
    private String bsplLedoCd;          // 본거지 법정도 코드
    private String ownrSe;              // 소유자 구분
    private String vhcty;               // 차종(등록정보)
    private String purps;               // 용도
    private String vin;                 // 차대번호
    private String fstRegDe;            // 최초 등록 일자
    private String vhclYridnw;          // 차량연식
    private String vhclMnfctDe;         // 차량 제작 일자
    private String emisInspEfctDe;      // 배출가스 검사 유효 일자
    private String srcmnno;             // 제원관리번호
    private String emisCrtcno;          // 배출가스 인증번호
    private String emisGrd;             // 배출가스 등급
    private String lemYn;               // 저공해조치 YN(사용안함)
    private String lemKnd;              // 저공해조치 종류
    private String vhclErsrYn;          // 차량말소 YN
    private String acqsDe;              // 취득일자
    private String acqsAmt;             // 취득금액
    private String vhcnm;               // 차명
    private String vhctyOg;             // 차종(제원정보)
    private String vhctyCl;             // 차종 분류
    private String vhctyTy;             // 차종 유형
    private String carFrm;              // 자동차 형식
    private String mkrNm;               // 제작사명
    private String fuel;                // 연료
    private String eginty;              // 엔진형식
    private String totWght;             // 총중량
    private String cryngWght;           // 적재중량
    private String eginpwr;             // 엔진출력
    private String dsplvl;              // 배기량
    private String tkcarNmpr;           // 승차인원
    private String vhclLt;              // 차량길이
    private String vhclWh;              // 차량너비
    private String vhclHg;              // 차량높이
    private String plorNm;              // 원산지명
    private String deleYn;              // 삭제 YN
    private String cnfrmDe;             // 승인 일자
    private String drvFrm;              // 구동 형식
    private String gearboxKnd;          // 변속기 종류
}
