package com.rhsnc.ovpe.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.rhsnc.ovpe.domain.CegCarMig;
import com.rhsnc.ovpe.domain.TbSrchKey;

@Mapper
public interface VhclSrchMapper {
    
    // 메인 그리드 조회
    List<TbSrchKey> selectTbSrchKeyList();

    // 서브 그리드 조회
    List<CegCarMig> selectMigDetail(
            @Param("vhcnm") String vhcnm,
            @Param("vhclYridnw") String vhclYridnw,
            @Param("eginty") String eginty,
            @Param("fuel") String fuel
    );
}
