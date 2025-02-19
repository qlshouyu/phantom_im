package com.iflytek.phantom.im.core.engine;

import com.iflytek.phantom.im.configuration.EngineProperties;
import com.iflytek.phantom.im.configuration.PhantomIMProperties;
import com.iflytek.phantom.im.utils.Constants;

import com.iflytek.phantom.im.utils.InetIPv6Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;


import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

/**
 * IM引擎消费者池，用于管理和分配IM引擎消费者实例
 *
 * @description: 管理多个IM引擎消费者实例，根据内容类型随机分配消费者
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@Slf4j
public class IMEngineConsumerPool {

    private Map<String, List<IMEngineConsumer>> consumers;
    private Random random;
    private Function<List<MQMessage>, Boolean> listener;
    private AtomicBoolean isStoped = new AtomicBoolean(true);


    public IMEngineConsumerPool(PhantomIMProperties properties, Function<List<MQMessage>, Boolean> listener) {
        this.consumers = new HashMap<>();
        this.random = new Random();
        this.listener = listener;
        EngineProperties engineProperties = properties.getEngine();
        engineProperties.getPoolContentTypes().forEach(poolContentType -> {
            List<IMEngineConsumer> consumers = new ArrayList<>();
            for (int i = 0; i < poolContentType.getSize(); i++) {
                IMEngineConsumer consumer = IMEngineConsumerFactory.getEngineProducer(properties.getEngine(), "ph_c_" + properties.getGroupTail(), this.listener);
                consumers.add(consumer);
            }
            this.consumers.put(poolContentType.getName(), consumers);
        });
    }


    public IMEngineConsumer get(Constants.PoolContentType type) {
        List<IMEngineConsumer> consumerList = this.consumers.get(type.getValue());
        int randomIndex = this.random.nextInt(consumerList.size());
        return consumerList.get(randomIndex);
    }

    public void start() {
        if(this.isStoped.compareAndSet(true,false)){
            this.consumers.forEach((contentType, consumerList) -> {
                consumerList.forEach(consumer -> {
                    try {
                        consumer.start(contentType);
                    } catch (Exception e) {
                        log.error("Failed to start consumer for contentType: {}", contentType, e);
                    }
                });
            });

        }

    }

    public void stop() {
        if(this.isStoped.compareAndSet(false,true)){
            this.consumers.forEach((contentType, consumerList) -> {
                consumerList.forEach(consumer -> {
                    try {
                        consumer.stop();
                    } catch (Exception e) {
                        log.error("Failed to stop consumer for contentType: {}", contentType, e);
                    }
                });
            });
        }

    }

}
