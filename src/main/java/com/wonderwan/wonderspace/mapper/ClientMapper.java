package com.wonderwan.wonderspace.mapper;

import com.wonderwan.wonderspace.model.entity.Client;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientMapper {

    Client queryClientById(@Param("clientId") String clientId);

    void createClient(Client client);
}
