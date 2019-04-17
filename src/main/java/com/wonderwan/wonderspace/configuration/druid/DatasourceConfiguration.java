package com.wonderwan.wonderspace.configuration.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.wonderwan.wonderspace.common.constant.WonderSpaceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DatasourceConfiguration {

    private Logger LOGGER = LoggerFactory.getLogger(DatasourceConfiguration.class);

    @Autowired
    private DruidDataSourceProperty druidDataSourceProperty;

    @Autowired
    private DruidMonitorProperty druidMonitorProperty;

    private Properties buildDruidConnectionProperty() {
        Properties connectProperties = new Properties();
        String connectionPropertiesStr = druidDataSourceProperty.getConnectionProperties();
        if (connectionPropertiesStr != null && !"".equals(connectionPropertiesStr)) {
            String[] propertiesList = connectionPropertiesStr.split(";");
            for (String propertiesTmp : propertiesList) {
                String[] obj = propertiesTmp.split("=");
                connectProperties.put(obj[0], obj[1]);
            }
            connectProperties.setProperty(WonderSpaceConstants.DRUID_PUBLIC_KEY_NAME,
                    druidDataSourceProperty.getPublicKey());
            connectProperties.setProperty(WonderSpaceConstants.DRUID_PASSWORD_NAME,
                    druidDataSourceProperty.getPassword());
        }
        return connectProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSource druidDataSource() throws Exception {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(druidDataSourceProperty.getDriverClassName());
        druidDataSource.setUrl(druidDataSourceProperty.getUrl());
        druidDataSource.setUsername(druidDataSourceProperty.getUsername());
        druidDataSource.setPassword(druidDataSourceProperty.getPassword());
        druidDataSource.setInitialSize(druidDataSourceProperty.getInitialSize());
        druidDataSource.setMinIdle(druidDataSourceProperty.getMinIdle());
        druidDataSource.setMaxActive(druidDataSourceProperty.getMaxActive());
        druidDataSource.setMaxWait(druidDataSourceProperty.getMaxWait());
        druidDataSource.setPasswordCallbackClassName(druidDataSourceProperty.getPasswordCallbackClassName());
        druidDataSource.setFilters(druidDataSourceProperty.getFilters());
        druidDataSource.setConnectProperties(this.buildDruidConnectionProperty());
        druidDataSource.init();
        return druidDataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean =
                new ServletRegistrationBean(new StatViewServlet(), druidMonitorProperty.getUrlMappings());
        servletRegistrationBean.addInitParameter("allow", druidMonitorProperty.getAllow());
        servletRegistrationBean.addInitParameter("deny", druidMonitorProperty.getDeny());
        servletRegistrationBean.addInitParameter("loginUsername", druidMonitorProperty.getLoginUsername());
        servletRegistrationBean.addInitParameter("loginPassword", druidMonitorProperty.getLoginPassword());
        servletRegistrationBean.addInitParameter("resetEnable", druidMonitorProperty.getResetEnable());
        return servletRegistrationBean;
    }

    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
