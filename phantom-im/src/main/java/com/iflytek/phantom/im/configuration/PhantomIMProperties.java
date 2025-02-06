package com.iflytek.phantom.im.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@Configuration
@ConfigurationProperties(prefix = "phantom.im")
@Data
public class PhantomIMProperties {

    private EngineProperties engine;

    public PhantomIMProperties() {
        this.engine = new EngineProperties();
    }

}
