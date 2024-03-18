package com.hixtrip.sample.app.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * redisson 配置
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "redisson")
public class RedissonConfig {
    public static final Long waiTime = 5L;

    public static final Long timeOutTime = 20L;


    @Value(value = "${redisson.host}")
    private String host;

    @Value(value = "${redisson.password:}")
    private String password;
}
