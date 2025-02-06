package com.iflytek.phantom.im.core.engine;

import com.iflytek.phantom.im.ws.AbstractJMPPMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@Slf4j
public class IMRocketMQConsumer implements IMEngineConsumer {
    private DefaultMQPushConsumer consumer;

    public IMRocketMQConsumer(String tenant, String app) {
        String group = "";
        if (StringUtils.isEmpty(tenant) && StringUtils.isEmpty(app)) {
            group = "phantom-im";
        } else {
            if (StringUtils.hasText(tenant)) {
                group += tenant;
            }
            if (StringUtils.hasText(app)) {
                group += "@" + app;
            }
            if (group.startsWith("@")) {
                group = group.substring(0, 1);
            }
        }
        this.consumer = new DefaultMQPushConsumer(group);
    }

    @Override
    public void consumer() throws Exception {
        log.info("Starting consumer");
        this.consumer.subscribe("phantom_im", "*");
        // 启动 Producer 实例
        this.consumer.start();
    }

    @Override
    public void init(String host) throws Exception {
        // 指定 NameServer 地址
        this.consumer.setNamesrvAddr(host);
        this.consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                log.info("Get msg:{}", msgs.size());
                msgs.forEach(m -> {
                    log.info("Get:{}", new String(m.getBody()));

                });
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

    }

    @Override
    public void destroy() {
        this.consumer.shutdown();
    }
}
