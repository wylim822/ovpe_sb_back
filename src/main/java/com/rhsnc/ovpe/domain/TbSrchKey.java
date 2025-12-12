package com.rhsnc.ovpe.domain;

import lombok.Data;

@Data
public class TbSrchKey {

    // TB_SRCH_KEY_NM_ENG
    private String vnmEng;
    private String vfulEng;
    private String engtEng;
    private Integer cntvmyEng;
    private Integer sntEng;

    // TB_SRCH_KEY_ALL
    private String vnmAll;
    private String vmyAll;
    private String vfulAll;
    private String engtAll;
    private Integer sntAll;
}
