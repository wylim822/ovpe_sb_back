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

    // !!추후수정
    Map<String, Object> callGptApi(CegCarMig vhclInfo, List<TbEetHisMe> inspInfoList);
}
