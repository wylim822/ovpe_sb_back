package com.rhsnc.ovpe.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.rhsnc.ovpe.domain.CegCarMig;
import com.rhsnc.ovpe.domain.TbEetHisMe;

@Mapper
public interface VhclSrchMapper {
    
    // 등록정보 조회
    CegCarMig selectVhclInfo(String carRegNo);

    // 검사정보 목록 조회
    List<TbEetHisMe> selectInspInfoList(String carRegNo);
    
}
