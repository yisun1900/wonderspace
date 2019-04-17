package com.wonderwan.wonderspace.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class SdwanServerVeth {

    private String serverVethId;

    private Integer vrfId;

    private String publicKey;

    private String privateKey;

    private String ipv4;

    private String ipv6;

    private String ipv4Netmask;

    private String ipv6Netmask;

    private String iface;

    private String token;

    private String version;

    private String certificate;

    private String listenPort;

    private String runMode;

    private String dns;

    private String peerEndpoint;

    private String status;

    private String boot;

    private Timestamp uptime;
}
