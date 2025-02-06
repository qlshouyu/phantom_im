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
public class IMEngineConsumerFactory {

    public static IMEngineConsumer getEngineProducer(EngineProperties properties) {
        IMEngineConsumer consumer = new IMRocketMQConsumer(properties.getTenant(), properties.getApp());
        try {
            consumer.init(properties.getHost());
            return consumer;
        } catch (Exception e) {
            log.error("Get consumer error:{}", e.getMessage());
            return null;
        }

    }


}
