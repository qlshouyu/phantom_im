package com.iflytek.phantom.im.core.engine;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/**
 * RocketMQ消息消费者实现类
 *
 * @description: 基于RocketMQ实现的消息消费者，支持按租户和应用隔离的消息订阅及顺序消费
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@Slf4j
public class IMRocketMQConsumer implements IMEngineConsumer {
    private DefaultMQPushConsumer consumer;
    private String addr;
    private String group;
    private Function<List<MQMessage>, Boolean> listener;
    // 默认1分钟不消费，消息丢弃发送
    private Long timeoutseconds = 60L;

    public IMRocketMQConsumer(String addr, String group, Function<List<MQMessage>, Boolean> listener) {
        this.addr = addr;
        this.group = group;
        this.listener = listener;
    }

    @Override
    public void start(String topic) throws Exception {
        log.info("Starting consumer for group:{} topic: {}", this.group, topic);
        this.consumer = new DefaultMQPushConsumer(this.group);
        // 指定 NameServer 地址
        this.consumer.setNamesrvAddr(addr);
        this.consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {

                Boolean result = listener.apply(msgs
                        .stream()
                        .filter(messageExt -> {
                            Boolean needSend = messageExt.getStoreTimestamp() + timeoutseconds * 1000 > System.currentTimeMillis();
                            if (!needSend) {
                                log.warn("The msg timeout,drop it, msg:{}", messageExt);
                            }
                            return needSend;
                        })
                        .map(messageExt -> new MQMessage(messageExt.getTopic(), messageExt.getBody(), messageExt.getProperties()))
                        .toList());
                return result ? ConsumeOrderlyStatus.SUCCESS : ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
            }
        });
        this.consumer.subscribe(topic, "*");
        this.consumer.start();
    }

    @Override
    public void stop() {
        this.consumer.shutdown();
    }
}
