package com.iflytek.phantom.im.ws.handler.iq;

import com.iflytek.phantom.im.core.User;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/6
 * @Version 1.0.0
 */
public abstract class IQOperationHandler<T> {


    public abstract Mono<Void> handler(User user, String type, IQMessage<T> iqBody);

    public abstract String name();
}
