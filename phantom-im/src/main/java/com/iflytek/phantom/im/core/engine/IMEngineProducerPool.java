package com.iflytek.phantom.im.core.engine;

import com.iflytek.phantom.im.configuration.EngineProperties;
import com.iflytek.phantom.im.configuration.PhantomIMProperties;
import com.iflytek.phantom.im.utils.Constants;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * IM引擎生产者池，用于管理和分配IM引擎生产者实例
 *
 * @description: 管理多个IM引擎生产者实例，提供生产者的获取和生命周期管理
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@Slf4j
@Component
public class IMEngineProducerPool {
    private Map<String, List<IMEngineProducer>> producers;
    private Random random;

    public IMEngineProducerPool(PhantomIMProperties properties) {
        this.producers = new HashMap<>();
        this.random = new Random();
        EngineProperties engineProperties = properties.getEngine();
        engineProperties.getPoolContentTypes().forEach(poolContentType -> {
            List<IMEngineProducer> producerList = new ArrayList<>();
            for (int i = 0; i < poolContentType.getSize(); i++) {
                IMEngineProducer producer = IMEngineProducerFactory.getEngineProducer(properties.getEngine(), "ph_p_" + properties.getGroupTail());
                producerList.add(producer);
            }
            this.producers.put(poolContentType.getName(), producerList);
        });
    }

    public IMEngineProducer get(Constants.PoolContentType type) {
        // 根据内容类型获取生产者列表
        List<IMEngineProducer> producerList = this.producers.get(type.getValue());

        // 如果没有找到对应的生产者列表，使用默认的第一个内容类型
        if (producerList == null || producerList.isEmpty()) {
            log.warn("未找到类型: {} 的生产者, 使用默认生产者", type);
            String defaultType = this.producers.keySet().iterator().next();
            producerList = this.producers.get(defaultType);
        }

        // 随机返回一个生产者实例
        int randomIndex = this.random.nextInt(producerList.size());
        return producerList.get(randomIndex);


    }

    public void start() {
        this.producers.forEach((contentType, producerList) -> {
            producerList.forEach(producer -> {
                try {
                    producer.start(contentType);
                } catch (Exception e) {
                    log.error("Failed to start producer for contentType: {}", contentType, e);
                }
            });
        });
    }

    public void stop() {
        this.producers.forEach((contentType, producerList) -> {
            producerList.forEach(producer -> {
                try {
                    producer.stop();
                } catch (Exception e) {
                    log.error("Failed to stop producer for contentType: {}", contentType, e);
                }
            });
        });
    }
}
