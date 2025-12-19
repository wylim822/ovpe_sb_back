package com.rhsnc.ovpe.domain;

import lombok.Data;

@Data
public class TbEetHisMe {

    // TB_EET_HIS_ME (검사정보)
    private String smoChkOrgNm;         // 정밀검사소명
    private String smoChkDate;          // 정밀검사일자
    private String smoChkType;          // 정밀검사종류
    private String smoChkRegNo;         // 정밀검사접수번호
    private String carRegNo;            // 차량번호
    private String carSerNo;            // 차대번호
    private String carSpecRegNo;        // 차량형식번호
    private String carNm;               // 차명
    private String carTypeNm;           // 차종
    private String carUsage;            // 차량용도
    private String carYear;             // 차량연식
    private String carFuelNm;           // 차량연료
    private String carMile;             // 주행거리
    private String carLength;           // 차량길이
    private String carWidth;            // 차량너비
    private String carHeight;           // 차량높이
    private String engineType;      	// 엔진형식
    private String engineCapacity;  	// 원동기배기량
    private String enginePower;     	// 엔진출력
    private String carWeight;       	// 차량중량
    private String totWeight;       	// 총중량
    private String carPassenger;   		// 정원
    private String cont;            	// 비고
    private String firstRegDate;    	// 최초등록일
    private String smoChkExpDate;   	// 정밀검사유효기간
    private String smoFunChkJudgeYn;    // 관능검사판정
    private String smoFunChkFailItem;   // 관능검사부적합내용
    private String smoChkMethod;    	// 정밀검사측정방법
    private String unloadSmoVal1;   	// 무부하매연측정치1
    private String unloadSmoLim1;   	// 무부하매연허용치1
    private String unloadSmoJudge1; 	// 무부하매연판정1
    private String unloadSmoVal2;   	// 무부하매연측정치2
    private String unloadSmoLim2;   	// 무부하매연허용치2
    private String unloadSmoJudge2; 	// 무부하매연판정2
    private String unloadSmoVal3;   	// 무부하매연측정치3
    private String unloadSmoLim3;   	// 무부하매연허용치3
    private String unloadSmoJudge3; 	// 무부하매연판정3
    private String unloadSmoVal4;   	// 무부하매연측정치4
    private String unloadSmoLim4;   	// 무부하매연허용치4
    private String unloadSmoJudge4; 	// 무부하매연판정4
    private String unloadSmoVal5;   	// 무부하매연측정치5
    private String unloadSmoLim5;   	// 무부하매연허용치5
    private String unloadSmoJudge5; 	// 무부하매연판정5
    private String co2Val;              // 이산화탄소값
    private String engineTorLim;        // 최대출력허용치
    private String engineTorVal;        // 최대출력측정값
    private String o2Val;           	// 산소값
    private String airTemperature;  	// 공기온도
    private String airPressure;     	// 공기압력
    private String smoChkJudgeYn;   	// 정밀검사판정
    private String reInsDate2;      	// 재검사기간
    private String smoChkExpDate2;  	// 정밀검사유효기간2
    private String smoFunChkRecNm;  	// 관능검사등록자명
    private String smoChkRecNm;     	// 정밀검사등록자명
    private String smoChkOrgOwnerNm;    // 정밀검사소대표자명    
    private String etcCont1;            // 기타내용1
    private String etcCont2;            // 기타내용2
    private String etcCont3;            // 기타내용3
    private String etcCont4;            // 기타내용4
    private String etcCont5;            // 기타내용5
    private String srcTbl;              // 원본대장테이블
    private String smoClockInfo;        // 정밀검사시간정보
    private String jobDate;             // 데이터 생성일자
    private String unloadSmoVal6;   	// 무부하매연측정치6
    private String unloadSmoLim6;   	// 무부하매연허용치6
    private String unloadSmoJudge6; 	// 무부하매연판정6
    private String fileNm;              // 수신파일명
    private String makeRegDate;         // 제작등록일자
    private String dataDbn;             // 데이터구분
    private String deleteGbn;           // 삭제구분
    private String deleteReason1;       // 삭제사유1
    private String deleteReason2;       // 삭제사유2
    private String deleteReason3;       // 삭제사유3
    private String deleteReason4;       // 삭제사유4
    
}
