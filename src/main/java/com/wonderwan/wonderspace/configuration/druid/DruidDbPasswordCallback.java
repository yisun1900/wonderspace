package com.wonderwan.wonderspace.configuration.druid;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.util.DruidPasswordCallback;
import com.wonderwan.wonderspace.common.constant.WonderSpaceConstants;

import java.util.Properties;

public class DruidDbPasswordCallback extends DruidPasswordCallback {

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        try {
            String publicKey = properties.getProperty(WonderSpaceConstants.DRUID_PUBLIC_KEY_NAME);
            String dbPassword = properties.getProperty(WonderSpaceConstants.DRUID_PASSWORD_NAME);
            setPassword(ConfigTools.decrypt(publicKey, dbPassword).toCharArray());
        } catch (Exception e) {
            setPassword("".toCharArray());
        }
    }
}