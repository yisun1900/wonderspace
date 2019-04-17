package com.wonderwan.wonderspace.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GenerateKeyService {

    public String createClientPriKey() {
        return UUID.randomUUID().toString();
    }

    public String createUserPriKey() {
        return UUID.randomUUID().toString();
    }

    public String createWonderBoxLicense() {
        return UUID.randomUUID().toString();
    }
}
