package com.iflytek.phantom.im.core.engine;

import com.iflytek.phantom.im.ws.AbstractJMPPMessage;

/**
 * IM引擎生产者接口
 * 
 * @description: 定义IM消息生产者的标准接口，负责消息的发送和生产
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
public interface IMEngineProducer {

    void producer(AbstractJMPPMessage message) throws Exception;

    void start(String topic) throws Exception;
    void stop();
}
