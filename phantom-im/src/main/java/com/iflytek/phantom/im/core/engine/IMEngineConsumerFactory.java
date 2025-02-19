package com.iflytek.phantom.im.core.engine;

import com.iflytek.phantom.im.configuration.EngineProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Function;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@Slf4j
public class IMEngineConsumerFactory {

    public static IMEngineConsumer getEngineProducer(EngineProperties properties,String group, Function<List<MQMessage>, Boolean> listener) {
        return new IMRocketMQConsumer(properties.getAddr(),group, listener);
    }


}
