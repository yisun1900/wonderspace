package com.wonderwan.wonderspace.service;

import com.wonderwan.wonderspace.mapper.ClientMapper;
import com.wonderwan.wonderspace.model.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientMapper clientMapper;

    public Client queryClientId(String clientId) {
        return clientMapper.queryClientById(clientId);
    }
}
