package com.rhsnc.ovpe.service;

import java.util.List;

import com.rhsnc.ovpe.domain.CegCarMig;
import com.rhsnc.ovpe.domain.TbSrchKey;

public interface VhclSrchService {

    // 메인 그리드 조회
    List<TbSrchKey> getSrchKeyList();

    // 서브 그리드 조회
    List<CegCarMig> getMigDetail(String vhcnm, String vhclYridnw, String eginty, String fuel);
}
