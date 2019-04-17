package com.wonderwan.wonderspace.service;

import com.wonderwan.wonderspace.mapper.SdwanClientLinkMapper;
import com.wonderwan.wonderspace.model.expand.SdwanClientLinkBO;
import com.wonderwan.wonderspace.model.param.SdwanClientLinkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SdwanClientLinkService {

    @Autowired
    private SdwanClientLinkMapper sdwanClientLinkMapper;

    public List<SdwanClientLinkBO> querySdwanClientChain(SdwanClientLinkVO sdwanClientLinkVO) {
        return sdwanClientLinkMapper.querySdwanClientChain(sdwanClientLinkVO);
    }
}
