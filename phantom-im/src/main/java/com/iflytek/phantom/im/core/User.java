package com.iflytek.phantom.im.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iflytek.phantom.im.domain.Jid;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;
import com.iflytek.phantom.im.ws.handler.stream.StartHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/1/25
 * @Version 1.0.0
 */
@Slf4j
public class User {


    @Getter
    @Setter
    private Jid jid;
    @JsonIgnore
    @Getter
    private WebSocketSession session;

    @Getter
    @Setter
    private StartHandler.StartBody globalInfo;


    @Getter
    private Set<String> tags;

    public User() {
        this.tags = new HashSet<>();
    }

    public User(String jid, WebSocketSession session) {
        this(jid, session, null);
    }

    public User(String jid, WebSocketSession session, List<String> tags) {
        this();
        this.jid = new Jid(jid);
        this.session = session;
        if (tags != null && !tags.isEmpty()) {
            this.tags.addAll(tags);
        }
    }

    // 判断用的tags中是否包含函数中给出的tags，只要包含一个就返回true
    public boolean containsTags(String... tags) {
      for (String tag : tags) {
        if (this.tags.contains(tag)) {
          return true;
        }
      }
      return false;
    }

    public Mono<Void> sendMessage(AbstractJMPPMessage msg) {
        return this.session.send(Mono.just(this.session.textMessage(msg.toString()))).doOnError(e -> {
            log.error("{} Send message error:{}", this.logPrefix(), e.getMessage());
        });
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public void setTags(List<String> tags) {
        this.tags = new HashSet<>(tags);
    }

    public void removeTag(String tag) {
        this.tags.remove(tag);
    }

    public String logPrefix() {
        return jid + "#" + session.getId();
    }
}
