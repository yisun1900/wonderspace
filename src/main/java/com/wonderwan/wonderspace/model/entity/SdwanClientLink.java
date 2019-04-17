package com.wonderwan.wonderspace.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class SdwanClientLink {

    private String peerId1;

    private String peerId2;

    private String status;

    private String boot;

    private Timestamp uptime;
}
