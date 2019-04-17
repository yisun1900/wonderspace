package com.wonderwan.wonderspace.service;

import com.wonderwan.wonderspace.mapper.SdwanClientVethMapper;
import com.wonderwan.wonderspace.model.entity.SdwanClientVeth;
import com.wonderwan.wonderspace.model.param.SdwanClientVethVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SdwanClientVethService {

    @Autowired
    private SdwanClientVethMapper sdwanClientVethMapper;

    public SdwanClientVeth queryWonderBox(SdwanClientVethVO sdwanClientVethVO) {
        return sdwanClientVethMapper.queryWonderBox(sdwanClientVethVO);
    }

    public List<SdwanClientVeth> queryWonderBoxByUserList(SdwanClientVethVO sdwanClientVethVO) {
        return sdwanClientVethMapper.queryWonderBoxByUserList(sdwanClientVethVO);
    }
}
