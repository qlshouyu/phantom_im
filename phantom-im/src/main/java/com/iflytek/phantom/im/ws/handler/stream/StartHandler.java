package com.iflytek.phantom.im.ws.handler.stream;

import com.iflytek.phantom.im.core.User;
import com.iflytek.phantom.im.core.UserManager;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;
import com.iflytek.phantom.im.ws.handler.StreamHandler;
import com.iflytek.phantom.im.ws.messages.PresencePongMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/6
 * @Version 1.0.0
 */
@Slf4j
public class StartHandler extends StreamHandler<StartHandler.StartBody> {


    @Override
    public Mono<Void> onHandler(User user, AbstractJMPPMessage<StartHandler.StartBody> content) {
        user.setGlobalInfo(content.getBody());
        content.setBody(null);
        content.setFrom(null);
        return user.sendMessage(content);
    }

    @Override
    public String type() {
        return "start";
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StartBody {
        // 版本
        private String v;
        private String tenant;
        private String app;
    }
}
