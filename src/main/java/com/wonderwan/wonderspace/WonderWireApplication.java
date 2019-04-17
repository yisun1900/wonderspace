package com.wonderwan.wonderspace;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.wonderwan.wonderspace.mapper")
@SpringBootApplication
@EnableCaching
public class WonderWireApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WonderWireApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
//        System.loadLibrary("chacha20poly1305jni");
    }
}
