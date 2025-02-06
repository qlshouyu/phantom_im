package com.iflytek.phantom.im.ws.handler.presence;

import com.iflytek.phantom.im.core.User;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;
import com.iflytek.phantom.im.ws.handler.IQHandler;
import com.iflytek.phantom.im.ws.handler.MessageHandler;
import com.iflytek.phantom.im.ws.messages.PresencePongMessage;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/6
 * @Version 1.0.0
 */
public class PingHandler extends MessageHandler {
    @Override
    public Mono<Void> onHandler(User user, AbstractJMPPMessage content) {
        PresencePongMessage pong = new PresencePongMessage(content.getId());
        return user.sendMessage(pong);
    }

    @Override
    public String type() {
        return "ping";
    }
}
