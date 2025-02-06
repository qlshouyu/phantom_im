package com.iflytek.phantom.im.core.engine;

import com.iflytek.phantom.im.configuration.PhantomIMProperties;
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
public class IMEngineConsumerPool implements InitializingBean {

    @Autowired
    private PhantomIMProperties properties;

    private List<IMEngineConsumer> consumers;
    private Random random;


    public IMEngineConsumerPool() {
        this.consumers = new ArrayList<>();
        this.random = new Random();
    }


    public IMEngineConsumer get() {
        int randomIndex = this.random.nextInt(this.consumers.size());
        return this.consumers.get(randomIndex);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (int i = 0; i < 2; i++) {
            this.consumers.add(IMEngineConsumerFactory.getEngineProducer(this.properties.getEngine()));
        }
    }
}
