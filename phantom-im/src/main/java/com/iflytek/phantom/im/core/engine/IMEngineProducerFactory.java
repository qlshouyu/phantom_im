package com.iflytek.phantom.im.core.engine;

import com.iflytek.phantom.im.configuration.EngineProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@Slf4j
public class IMEngineProducerFactory {

    public static IMEngineProducer getEngineProducer(EngineProperties properties, String group) {
        IMEngineProducer imEngineProducer = new IMRocketMQProducer(properties.getAddr(), group);
        return imEngineProducer;
    }


}
