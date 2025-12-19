package com.rhsnc.ovpe.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.rhsnc.ovpe.domain.CegCarMig;
import com.rhsnc.ovpe.domain.TbEetHisMe;

@Mapper
public interface VhclAnlsMapper {
    // 등록정보 조회
    CegCarMig selectVhclInfo(String carRegNo);

    // 검사정보 목록 조회
    List<TbEetHisMe> selectInspInfoList(String carRegNo);


    // 분석DB 조회용 변수
    Map<String, Object> selectLatestInspVarsG(String carRegNo);     // 휘발유

    Map<String, Object> selectLatestInspVarsD(String carRegNo);     // 경유

    // 분석DB 조회
    List<String> selectAnlsRptAllG(Map<String,Object> anlsVars);    // 휘발유

    List<String> selectAnlsRptAllD(Map<String,Object> anlsVars);    // 경유

    // API SYSTEM TEXT 조회
    String selectApiSystemText(Map<String,Object> inspInfo);
    
}
