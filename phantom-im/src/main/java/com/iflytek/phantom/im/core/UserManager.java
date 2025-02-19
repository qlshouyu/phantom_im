package com.iflytek.phantom.im.core;

import com.iflytek.phantom.im.configuration.PhantomIMProperties;
import com.iflytek.phantom.im.core.engine.IMEngineConsumerPool;
import com.iflytek.phantom.im.core.engine.MQMessage;
import com.iflytek.phantom.im.service.TagService;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IM用户管理器
 *
 * @description: 管理IM系统中的用户信息，处理用户的在线状态和会话管理
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@Component
public class UserManager {
    private static final Logger log = LoggerFactory.getLogger(UserManager.class);
    private Map<String, User> users;

    private IMEngineConsumerPool consumerPool;

    private TagService tagService;

    public UserManager(PhantomIMProperties properties, TagService tagService) {
        this.users = new ConcurrentHashMap<>();
        this.consumerPool = new IMEngineConsumerPool(properties, this::listener);
        this.tagService = tagService;
    }

    /**
     * 监听并处理消息的方法
     * 该方法接收一个MQ消息列表，遍历每个消息，根据消息的目标标签获取用户，并向这些用户发送消息
     *
     * @param messages MQ消息列表，包含待处理的消息
     * @return boolean 总是返回true，表示方法执行完毕
     */
    private boolean listener(List<MQMessage> messages) {
        if (messages != null) {
            for (MQMessage message : messages){

                log.info("Consumer msg:{}", message);
                AbstractJMPPMessage jmppMessage = message.getObjBody();
                switch (jmppMessage.getENCategory()) {
                    case message -> {
                        AbstractJMPPMessage.MESSAGE_TYPE type=null;
                        try {
                            type=jmppMessage.getENType();
                        }catch (Exception e){
                            log.warn("Not support the type:{}",jmppMessage.getType());
                            return true;
                        }
                        switch (type) {
                            case headline -> {
                                List<String> tags = new ArrayList<>();
                                List<String> jids = new ArrayList<>();
                                jmppMessage.getTo().forEach(tag -> {
                                    String strTo = (String) tag;
                                    if (strTo.startsWith("t@")) {
                                        tags.add(strTo.substring(2));
                                    } else if (strTo.startsWith("c@")) {
                                        jids.add(strTo.substring(2));
                                    }
                                });
                                // send by tag
                                if (!tags.isEmpty()) {
                                    Set<User> users = this.tagService.getUserByTags(tags);
                                    if (!users.isEmpty()) {
                                        users.forEach(user -> {
                                            user.sendMessage(jmppMessage).subscribe();
                                        });
                                    }
                                }

                                // send by user jid
                                if (!jids.isEmpty()) {
                                    jids.forEach(jid -> {
                                        User user = this.users.get(jid);
                                        if (user != null) {
                                            user.sendMessage(jmppMessage).subscribe();
                                        }
                                    });
                                }

                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public void addUser(User user) {
        if (this.users.isEmpty()) {
            synchronized (this) {
                if (this.users.isEmpty()) {
                    this.consumerPool.start();
                }
            }
        }
        this.users.put(user.getJid().getJid(), user);

    }

    public User deleteUser(String jid) {
        User user = this.users.remove(jid);
        if (user != null) {
            synchronized (this) {
                if (this.users.isEmpty()) {
                    this.consumerPool.stop();
                }
            }
        }
        return user;
    }

    public User get(String jid) {
        return this.users.get(jid);
    }
}
