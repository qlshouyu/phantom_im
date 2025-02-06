package com.iflytek.phantom.im.core.engine;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
public interface IMEngineConsumer {

    void consumer() throws Exception;

    void init(String host) throws Exception;

    void destroy();
}
