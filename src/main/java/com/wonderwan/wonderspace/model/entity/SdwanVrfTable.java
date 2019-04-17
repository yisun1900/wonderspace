package com.wonderwan.wonderspace.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class SdwanVrfTable {

    private Integer tableId;

    private Integer communityId;

    private String status;

    private String boot;

    private Timestamp uptime;
}
