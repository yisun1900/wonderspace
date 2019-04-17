package com.wonderwan.wonderspace.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {

    private String cid;

    private String username;

    private String password;

    private String nickname;

    private String phone;

    private String email;

    private String companyId;

    private String status;
}
