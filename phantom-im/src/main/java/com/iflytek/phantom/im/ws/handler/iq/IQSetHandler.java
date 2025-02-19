package com.iflytek.phantom.im.ws.handler.iq;

import com.fasterxml.jackson.core.type.TypeReference;
import com.iflytek.phantom.im.core.User;
import com.iflytek.phantom.im.service.TagService;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;
import com.iflytek.phantom.im.ws.handler.IQHandler;
import com.iflytek.phantom.im.ws.messages.AckJMPPMessage;
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
public class IQSetHandler extends IQHandler {

    private Map<String, IQOperationHandler> iqOperationHandlers;

    public IQSetHandler(TagService tagService) {
        this.iqOperationHandlers = new HashMap<>();
        // Tag handler
        IQOperationHandler iqTagHandler = new IQOperationTagHandler(tagService);
        this.iqOperationHandlers.put(iqTagHandler.name(), iqTagHandler);
    }

    @Override
    public Mono<Void> onHandler(User user, AbstractJMPPMessage content) {
        IQMessage<List<String>> iqMessage = content.getObjectBody(new TypeReference<IQMessage<List<String>>>() {
        });
        IQOperationHandler operationHandler = this.iqOperationHandlers.get(iqMessage.getName());
        if (operationHandler != null) {
            return operationHandler
                    .handler(user, content.getENType(), iqMessage)
                    .doOnSuccess(v -> {
                        AckJMPPMessage ask = AckJMPPMessage.buildSuccess(content.getId(), content.getENCategory(), user.getJid().getJid());
                        user.sendMessage(ask).subscribe();
                    });
        }
        return Mono.empty();
    }

    @Override
    public String type() {
        return "set";
    }
}
