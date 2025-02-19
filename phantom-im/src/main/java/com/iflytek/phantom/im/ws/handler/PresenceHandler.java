package com.iflytek.phantom.im.ws.handler;

import com.iflytek.phantom.im.core.User;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/6
 * @Version 1.0.0
 */
public abstract class PresenceHandler extends AbstractHandler {


    @Override
    public String category() {
        return "presence";
    }
}
