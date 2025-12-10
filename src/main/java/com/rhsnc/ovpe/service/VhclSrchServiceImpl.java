package com.rhsnc.ovpe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rhsnc.ovpe.domain.CegCarMig;
import com.rhsnc.ovpe.domain.TbEetHisMe;
import com.rhsnc.ovpe.mapper.VhclSrchMapper;

@Service
public class VhclSrchServiceImpl implements VhclSrchService {

    private final VhclSrchMapper vhclSrchMapper;

    public VhclSrchServiceImpl(VhclSrchMapper vhclSrchMapper) {
        this.vhclSrchMapper = vhclSrchMapper;
    }
    
    // 등록정보 조회
    @Override
    public CegCarMig getVhclInfo(String carRegNo){
        return vhclSrchMapper.selectVhclInfo(carRegNo);
    }

    // 검사정보 목록 조회
    @Override
    public List<TbEetHisMe> getInspInfoList(String carRegNo) {
        return vhclSrchMapper.selectInspInfoList(carRegNo);
    }

    // chatGpt API 호출 (!!추후 수정)
    @Override
    public Map<String, Object> callGptApi(CegCarMig vhclInfo){
        Map<String, Object> apiRslt = new HashMap<>();

        // !!실제 API 호출 로직 작성 필요

        // !!차량번호 최초 조회인 경우, 조회결과 DB 저장 로직 작성 필요

        // @@테스트 결과값
        // apiRslt.put("summary", "이 차량은 2020년식 아반떼로, 주행거리 대비 상태가 양호한 편입니다. SmartStream 엔진 특성상 연비 효율이 우수하며, 최근 정밀검사에서도 큰 문제 없이 통과한 것으로 보입니다. 첫차 또는 출퇴근용으로 무난하며, 사고 이력 조회 시 큰 문제가 없다면 구매 가치는 충분합니다.");
        // apiRslt.put("cmt", "특이사항 없음");

        //System.out.println("@@ Service2 result: " + apiRslt);
        return apiRslt;
    }

}
