package com.wonderwan.wonderspace.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class User {

    private String uid;

    private String cid;

    private String uuid;

    private String serverVethId;

    private String clientVethId;

    private Timestamp expireDt;

    private String userType;

    private Integer bandwidth;

    private String license;

    private Integer services;

    private String status;
}
