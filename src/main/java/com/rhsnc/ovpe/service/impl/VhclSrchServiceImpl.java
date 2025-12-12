package com.rhsnc.ovpe.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rhsnc.ovpe.domain.CegCarMig;
import com.rhsnc.ovpe.domain.TbSrchKey;
import com.rhsnc.ovpe.mapper.VhclSrchMapper;
import com.rhsnc.ovpe.service.VhclSrchService;

@Service
public class VhclSrchServiceImpl implements VhclSrchService {

    private final VhclSrchMapper vhclSrchMapper;

    public VhclSrchServiceImpl(VhclSrchMapper vhclSrchMapper) {
        this.vhclSrchMapper = vhclSrchMapper;
    }

    // 메인 그리드 조회
    @Override
    public List<TbSrchKey> getSrchKeyList() {
        return vhclSrchMapper.selectTbSrchKeyList();
    }

    // 서브 그리드 조회
    @Override
    public List<CegCarMig> getMigDetail(String vhcnm, String vhclYridnw, String eginty, String fuel) {
        return vhclSrchMapper.selectMigDetail(vhcnm, vhclYridnw, eginty, fuel);
    }
    
}
