package com.phamtom.im.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/20
 * @Version 1.0.0
 */
@ConfigurationProperties(prefix = PHIProperties.PHI_PREFIX)
@Data
public class PHIProperties {
    public final static String PHI_PREFIX = "phantom.im";

    private String url = "http://localhost:8789";
}
