package com.rhsnc.ovpe.service;

import java.util.List;
import java.util.Map;

import com.rhsnc.ovpe.domain.CegCarMig;
import com.rhsnc.ovpe.domain.TbEetHisMe;

public interface VhclAnlsService {
    // 등록정보 조회
    CegCarMig getVhclInfo(String carRegNo);

    // 검사정보 목록 조회
    List<TbEetHisMe> getInspInfoList(String carRegNo);

    // 분석DB 조회 및 ChatGPT API 호출
    Map<String, Object> getVhclAnlsInfo(Map<String, Object> vhclInfo);

    // API 호출 테스트 (!!추후 정리 필요)
    Map<String, Object> getCallApiRslt(Map<String,Object> req);
}
