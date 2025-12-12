package com.rhsnc.ovpe.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhsnc.ovpe.domain.CegCarMig;
import com.rhsnc.ovpe.domain.TbSrchKey;
import com.rhsnc.ovpe.service.VhclSrchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vhcl")
public class VhclSrchController {
    private final VhclSrchService vhclSrchService;

    // 메인 그리드 조회
    @GetMapping("/tbSrchKeyList")
    public List<TbSrchKey> list() {
        return vhclSrchService.getSrchKeyList();
    }

    // 서브 그리드 조회
    @GetMapping("/migDetail")
    public List<CegCarMig> migDetail(
            @RequestParam String vhcnm,
            @RequestParam String vhclYridnw,
            @RequestParam String eginty,
            @RequestParam String fuel
    ) {
        return vhclSrchService.getMigDetail(vhcnm, vhclYridnw, eginty, fuel);
    }
}
