package com.phamtom.im.client;

import com.phamtom.im.client.core.DefaultPushClient;
import com.phamtom.im.client.core.PushClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/20
 * @Version 1.0.0
 */
@Configuration(
        proxyBeanMethods = false
)
@Import({PHIProperties.class})
public class PhantomIMAutoconfiguration {


    // 创建PushClient的bean
    @Bean
    public PushClient pushClient(PHIProperties phiProperties) {
        return new DefaultPushClient(phiProperties.getUrl());
    }
}
