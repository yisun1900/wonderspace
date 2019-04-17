package com.wonderwan.wonderspace.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class SdwanClientVeth {

    private String clientVethId;

    private String publicKey;

    private String privateKey;

    private String version;

    private String ipv4;

    private String ipv6;

    private String ipv4Netmask;

    private String ipv6Netmask;

    private String status;

    private String boot;

    private Timestamp uptime;

    private String allowedIps;

    private Integer keepAlive;
}
