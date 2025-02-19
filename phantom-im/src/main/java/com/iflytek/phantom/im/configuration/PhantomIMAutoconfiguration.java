package com.iflytek.phantom.im.configuration;

import com.iflytek.phantom.im.utils.InetIPv6Utils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@Configuration
public class PhantomIMAutoconfiguration {
    @Bean
    public InetIPv6Utils inetIPv6Utils(InetUtilsProperties properties) {
        return new InetIPv6Utils(properties);
    }
}
