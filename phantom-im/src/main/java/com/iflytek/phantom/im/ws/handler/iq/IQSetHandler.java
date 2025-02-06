package com.iflytek.phantom.im.ws.handler.iq;

import com.iflytek.phantom.im.core.User;
import com.iflytek.phantom.im.service.TagService;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;
import com.iflytek.phantom.im.ws.handler.IQHandler;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/6
 * @Version 1.0.0
 */
public class IQSetHandler extends IQHandler<IQMessage<List<String>>> {

    private Map<String, IQOperationHandler> iqOperationHandlers;

    public IQSetHandler() {
        this.iqOperationHandlers = new HashMap<>();
        // Tag handler
        IQOperationHandler iqTagHandler = new IQOperationTagHandler(new TagService());
        this.iqOperationHandlers.put(iqTagHandler.name(), iqTagHandler);
    }

    @Override
    public Mono<Void> onHandler(User user, AbstractJMPPMessage<IQMessage<List<String>>> content) {
        IQOperationHandler operationHandler = this.iqOperationHandlers.get(content.getBody().getName());
        if (operationHandler == null) {
            return operationHandler.handler(user, this.type(), content.getBody());
        }
        return Mono.empty();
    }

    @Override
    public String type() {
        return "set";
    }
}
