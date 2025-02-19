package com.iflytek.phantom.im.ws.handler;

import com.iflytek.phantom.im.core.User;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: Phantom Bee
 * @create: 2024/12/27
 * @Version 1.0.0
 */
public abstract class AbstractHandler {

    public abstract Mono<Void> onHandler(User user, AbstractJMPPMessage content);


    public abstract String type();

    public abstract String category();
}
