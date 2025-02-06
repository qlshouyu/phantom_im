package com.iflytek.phantom.im.core.engine;

import com.iflytek.phantom.im.ws.AbstractJMPPMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@Slf4j
public class IMRocketMQProducer implements IMEngineProducer {
    private DefaultMQProducer producer;

    public IMRocketMQProducer(String tenant, String app) {
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
        this.producer = new DefaultMQProducer(group);
    }

    @Override
    public void producer(AbstractJMPPMessage message) throws Exception {
        Message msg = new Message();
        msg.setBody(message.toString().getBytes(StandardCharsets.UTF_8));
        msg.setTopic("phantom_im");
        log.info("Start send");
        SendResult result = this.producer.send(msg);
        if (result.getSendStatus() == SendStatus.SEND_OK) {
            log.info("Send ok");
        }
    }

    @Override
    public void init(String host) throws Exception {
        // 指定 NameServer 地址
        this.producer.setNamesrvAddr(host);
        // 启动 Producer 实例
        this.producer.start();
    }

    @Override
    public void destroy() {
        this.producer.shutdown();
    }
}
