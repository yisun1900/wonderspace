package com.wonderwan.wonderspace.mapper;

import com.wonderwan.wonderspace.model.entity.SdwanServerVeth;
import com.wonderwan.wonderspace.model.param.SdwanServerVethVO;
import org.springframework.stereotype.Repository;

@Repository
public interface SdwanServerVethMapper {

    SdwanServerVeth queryWonderSpace(SdwanServerVethVO sdwanServerVethVO);
}
