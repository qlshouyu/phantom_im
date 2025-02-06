package com.iflytek.phantom.im.core;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/1/25
 * @Version 1.0.0
 */
@Component
public class UserManager {
    private Map<String, User> users;

    public UserManager() {
        this.users = new ConcurrentHashMap<>();
    }

    public void addUser(User user) {
        this.users.put(user.getJid().getJid(), user);
    }

    public User deleteUser(String jid) {
        return this.users.remove(jid);
    }

    public User get(String jid) {
        return this.users.get(jid);
    }
}
