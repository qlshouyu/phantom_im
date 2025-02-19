package com.iflytek.phantom.im.ws;

import com.iflytek.phantom.im.core.User;
import com.iflytek.phantom.im.core.UserManager;
import com.iflytek.phantom.im.utils.WSQuery;
import com.iflytek.phantom.im.ws.handler.ComposeHandler;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.channel.AbortedException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


/**
 * Websocket通道
 * 第 1 位
 * 0000 0001 1 创建房间的权限
 * 第 2 位
 * 0000 0010 2 进入房间的权限
 * 第 3 位
 * 0000 0100 4 发送语音的权限
 * 第 4 位
 * 0000 1000 8 接收语音的权限
 * 第 5 位
 * 0001 0000 16 发送视频的权限
 * 第 6 位
 * 0010 0000 32 接收视频的权限
 * 第 7 位
 * 0100 0000 64 发送辅路（也就是屏幕分享）视频的权限
 * 第 8 位
 * 1000 0000 128 接收辅路（也就是屏幕分享）视频的权限
 * 第 9 位
 * 1 0000 0000 256 踢人权限
 * 第 10 位
 * 10 0000 0000 512 解散房间的权限
 */
@Slf4j
public class WebsocketTransport implements WebSocketHandler {

    private UserManager userManager;
    private ComposeHandler composeHandler;


    public static void main(String[] args) {
        try {
            URI uri = new URI("ws://localhost:8789/phantom_im");
            String uriq = uri.getQuery();
            System.out.printf("ddd%s", uriq);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public WebsocketTransport(UserManager userManager, ComposeHandler composeHandler) {
        this.userManager = userManager;
        this.composeHandler = composeHandler;
    }


    @Override
    public Mono<Void> handle(WebSocketSession session) {
        log.info("{}:Add new client", session.getId());
        WSQuery query = new WSQuery(session.getHandshakeInfo().getUri());
        // String appId = query.get("app");
        String jid = query.get("jid");
        User user = new User(jid, session);
        userManager.addUser(user);
        return session.receive().doOnSubscribe(s -> {
            log.info("{} doOnSubscribe", user.logPrefix());
        }).doOnTerminate(() -> {
            log.info("{} doOnTerminate", user.logPrefix());
        }).doOnCancel(() -> {
            log.info("{} doOnCancel", user.logPrefix());
            userManager.deleteUser(user.getJid().getJid());
        }).doOnNext(message -> {
            if (message.getType().equals(WebSocketMessage.Type.BINARY)) {
                log.info("{} Receive binary message", user.logPrefix());
            } else if (message.getType().equals(WebSocketMessage.Type.TEXT)) {
                String content = message.getPayloadAsText();
                if (StringUtils.isNotEmpty(content)) {
                    composeHandler.onHandler(user, AbstractJMPPMessage.fromString(content)).subscribe();
                } else {
                    log.warn("{} Get empty message", user.logPrefix());
                }
            } else if (message.getType().equals(WebSocketMessage.Type.PING)) {
                log.debug("{}:Get ping", user.logPrefix());
                session.send(Flux.just(session.pongMessage(s -> s.wrap(new byte[256])))).subscribe();
            } else if (message.getType().equals(WebSocketMessage.Type.PONG)) {
                log.debug("{}:Get pong", user.logPrefix());
            }
        }).doOnError(e -> {
            if (e instanceof AbortedException) {
                log.warn("{} AbortedException:{}", user.logPrefix(), e.getMessage());
            } else if (e instanceof IOException) {
                log.warn("{} IOException:{}", user.logPrefix(), e.getMessage());
            } else {
                log.error("{} Error:", user.logPrefix(), e);
            }
        }).doFinally(__ -> {
            log.info("{} doFinally", user.logPrefix());
            userManager.deleteUser(user.getJid().getJid());
        }).then();
    }


}

