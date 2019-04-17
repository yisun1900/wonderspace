package com.wonderwan.wonderspace.model.expand;

import com.wonderwan.wonderspace.model.entity.SdwanClientLink;
import com.wonderwan.wonderspace.model.entity.SdwanClientVeth;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SdwanClientLinkBO extends SdwanClientLink {

    private SdwanClientVeth sdwanClientVeth1;

    private SdwanClientVeth sdwanClientVeth2;
}
