package com.iflytek.phantom.im.core.engine;

import com.iflytek.phantom.im.configuration.PhantomIMProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@Component
public class IMEngineProducerPool implements InitializingBean {

    @Autowired
    private PhantomIMProperties properties;
    private List<IMEngineProducer> producers;
    private Random random;


    public IMEngineProducerPool() {
        this.producers = new ArrayList<>();
        this.random = new Random();
    }


    public IMEngineProducer get() {
        int randomIndex = this.random.nextInt(this.producers.size());
        return this.producers.get(randomIndex);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (int i = 0; i < 2; i++) {
            this.producers.add(IMEngineProducerFactory.getEngineProducer(this.properties.getEngine()));
        }
    }
}
