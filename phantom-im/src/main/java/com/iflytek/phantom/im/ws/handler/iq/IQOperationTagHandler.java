package com.iflytek.phantom.im.ws.handler.iq;

import com.iflytek.phantom.im.core.User;
import com.iflytek.phantom.im.service.TagService;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/6
 * @Version 1.0.0
 */
@Slf4j
public class IQOperationTagHandler extends IQOperationHandler<List<String>> {

    private TagService tagService;

    public IQOperationTagHandler(TagService service) {
        this.tagService = service;
    }

    @Override
    public Mono<Void> handler(User user, AbstractJMPPMessage.MESSAGE_TYPE type, IQMessage<List<String>> iqBody) {
        switch (type) {
            case add -> {
                return this.add(user, iqBody);
            }
            case set -> {
                return this.set(user, iqBody);
            }
        }
        return Mono.empty();
    }

    private Mono<Void> add(User user, IQMessage<List<String>> iqBody) {
        return Mono.just(iqBody).doOnNext(b -> {
            log.info("Add tag:{}", user.logPrefix(), iqBody.getBody());
            this.tagService.addUserTags(user, b.getBody());
        }).then();
    }

    private Mono<Void> set(User user, IQMessage<List<String>> iqBody) {

        return Mono.just(iqBody).doOnNext(b -> {
            Set<String> oldTags = user.getTags();
            log.info("Set tags:{} {}", user.logPrefix(), oldTags);
            // Set new tags
            user.setTags(iqBody.getBody());
            this.tagService.setUserTags(user, oldTags, b.getBody());
        }).then();
    }

    public void delete(User user, IQMessage<List<String>> iqBody) {

    }

    @Override
    public String name() {
        return "tag";
    }
}
