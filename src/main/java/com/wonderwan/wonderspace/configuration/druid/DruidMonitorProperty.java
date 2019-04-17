package com.wonderwan.wonderspace.configuration.druid;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "druid")
public class DruidMonitorProperty {

    private String loginUsername;

    private String loginPassword;

    private String allow;

    private String deny;

    private String resetEnable;

    private String urlMappings;
}
