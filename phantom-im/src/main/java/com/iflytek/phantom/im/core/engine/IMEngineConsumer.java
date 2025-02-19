package com.iflytek.phantom.im.core.engine;

/**
 * IM引擎消费者接口
 * 
 * @description: 定义IM消息消费者的标准接口，包含消息订阅启动和销毁等基本操作
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
public interface IMEngineConsumer {

    void start(String topic) throws Exception;

    void stop();
    

}
