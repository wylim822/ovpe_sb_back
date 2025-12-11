package com.rhsnc.ovpe.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhsnc.ovpe.service.VhclSrchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vhcl")
public class VhclSrchController {
    private final VhclSrchService vhclSrchService;

    
}
