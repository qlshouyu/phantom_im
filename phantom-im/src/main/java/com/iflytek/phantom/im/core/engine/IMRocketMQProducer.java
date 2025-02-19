package com.iflytek.phantom.im.core.engine;

import com.iflytek.phantom.im.utils.Constants;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;
import com.iflytek.phantom.im.ws.messages.HeadlineJMPPMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * RocketMQ消息生产者实现类
 *
 * @description: 基于RocketMQ实现的消息生产者，支持消息的异步发送
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@Slf4j
public class IMRocketMQProducer implements IMEngineProducer {
    private DefaultMQProducer producer;

    public IMRocketMQProducer(String addr,String gourp) {
        this.producer = new DefaultMQProducer(gourp);
        this.producer.setNamesrvAddr(addr);
    }

    @Override
    public void producer(AbstractJMPPMessage message) throws Exception {
        if (message instanceof HeadlineJMPPMessage) {
            Message msg = new Message(Constants.PoolContentType.PUSH.getValue(), message.toString().getBytes(StandardCharsets.UTF_8));
            this.producer.send(msg);
        }
    }

    @Override
    public void start(String topic) throws Exception {
        log.info("Starting producer for topic: {}", topic);
        this.producer.start();
    }


    @Override
    public void stop() {
        this.producer.shutdown();
    }
}
