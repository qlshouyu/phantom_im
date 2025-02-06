package com.iflytek.phantom.im.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngineProperties {
    private String host;
    private String tenant;
    private String app;
}
