package com.rhsnc.ovpe.service.impl;

import org.springframework.stereotype.Service;

import com.rhsnc.ovpe.mapper.VhclSrchMapper;
import com.rhsnc.ovpe.service.VhclSrchService;

@Service
public class VhclSrchServiceImpl implements VhclSrchService {

    private final VhclSrchMapper vhclSrchMapper;

    public VhclSrchServiceImpl(VhclSrchMapper vhclSrchMapper) {
        this.vhclSrchMapper = vhclSrchMapper;
    }
    
}
