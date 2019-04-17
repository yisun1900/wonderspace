package com.wonderwan.wonderspace.mapper;

import com.wonderwan.wonderspace.model.entity.SdwanClientVeth;
import com.wonderwan.wonderspace.model.param.SdwanClientVethVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SdwanClientVethMapper {

    SdwanClientVeth queryWonderBox(SdwanClientVethVO sdwanClientVethVO);

    List<SdwanClientVeth> queryWonderBoxByUserList(SdwanClientVethVO sdwanClientVethVO);

    void updateWonderBox(SdwanClientVethVO sdwanClientVethVO);
}