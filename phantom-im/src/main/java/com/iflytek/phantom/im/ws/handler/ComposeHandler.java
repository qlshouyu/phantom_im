package com.iflytek.phantom.im.ws.handler;

import com.iflytek.phantom.im.core.User;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Phantom Bee
 * @create: 2024/12/27
 * @Version 1.0.0
 */
@Slf4j
public class ComposeHandler extends AbstractHandler<Object> {
    private Map<String, Map<String, AbstractHandler>> adapterHandlers;

    public ComposeHandler(List<AbstractHandler> handlers) {
        this.adapterHandlers = new HashMap<>();
        handlers.forEach(h -> {
            log.info("Add handler:{} {}", h.category(), h.type());
            Map<String, AbstractHandler> subHandlers = this.adapterHandlers.get(h.category());
            if (subHandlers == null) {
                subHandlers = new HashMap<>();
                this.adapterHandlers.put(h.category(), subHandlers);
            }
            subHandlers.put(h.type(), h);
        });
    }

    @Override
    public Mono<Void> onHandler(User user, AbstractJMPPMessage content) {
        Map<String, AbstractHandler> category = this.adapterHandlers.get(content.getC());
        if (category != null) {
            AbstractHandler handler = category.get(content.getType());
            if (handler != null) {
                return handler.onHandler(user, content);
            } else {
                log.warn("Not found handler: type {}", content.getType());
                return Mono.empty();
            }
        } else {
            log.warn("Not found handler: category{}", content.getC());
            return Mono.empty();
        }

    }

    @Override
    public String type() {
        return "compose";
    }

    @Override
    public String category() {
        return "compose";
    }
}
