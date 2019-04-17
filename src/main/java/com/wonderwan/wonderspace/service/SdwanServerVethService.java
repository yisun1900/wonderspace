package com.wonderwan.wonderspace.service;

import com.wonderwan.wonderspace.mapper.SdwanServerVethMapper;
import com.wonderwan.wonderspace.model.entity.SdwanServerVeth;
import com.wonderwan.wonderspace.model.param.SdwanServerVethVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SdwanServerVethService {

    @Autowired
    private SdwanServerVethMapper sdwanServerVethMapper;

    public SdwanServerVeth queryWonderSpace(SdwanServerVethVO sdwanServerVethVO) {
        return sdwanServerVethMapper.queryWonderSpace(sdwanServerVethVO);
    }
}
