package com.wonderwan.wonderspace.mapper;

import com.wonderwan.wonderspace.model.expand.SdwanClientLinkBO;
import com.wonderwan.wonderspace.model.param.SdwanClientLinkVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SdwanClientLinkMapper {

    List<SdwanClientLinkBO> querySdwanClientChain(SdwanClientLinkVO sdwanClientLinkVO);
}
