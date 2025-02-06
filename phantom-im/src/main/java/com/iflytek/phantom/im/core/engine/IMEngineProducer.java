package com.iflytek.phantom.im.core.engine;

import com.iflytek.phantom.im.ws.AbstractJMPPMessage;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
public interface IMEngineProducer {

    void producer(AbstractJMPPMessage message) throws Exception;

    void init(String host) throws Exception;

    void destroy();
}
