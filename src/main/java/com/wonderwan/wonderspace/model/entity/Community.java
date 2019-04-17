package com.wonderwan.wonderspace.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Community {

    private Integer communityId;

    private String communityType;

    private Integer aclId;

    private String aclIp;
}
