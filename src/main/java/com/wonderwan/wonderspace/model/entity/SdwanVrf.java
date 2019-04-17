package com.wonderwan.wonderspace.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class SdwanVrf {

    private Integer vrfId;

    private Integer deviceName;

    private Integer tableId;

    private String status;

    private String boot;

    private Timestamp uptime;
}
