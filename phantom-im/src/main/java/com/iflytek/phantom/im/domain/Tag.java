package com.iflytek.phantom.im.domain;

import com.iflytek.phantom.im.core.User;
import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
public class Tag {

    @Getter
    private String tag;
    private Map<String, User> users;
    private ReentrantLock lock;

    public Tag(String tag, ReentrantLock lock) {
        this.tag = tag;
        this.users = new HashMap<>();
        this.lock = lock;
    }

    public void addUser(User user) {
        this.users.put(user.getJid().getEid(), user);
    }

    public int delUser(User user) {
        this.users.remove(user.getJid().getEid());
        return this.users.size();
    }

    public Collection<User> getUsers() {
        return this.users.values();
    }

    public boolean isEmpty() {
        return this.users.isEmpty();
    }

    public void lock() {
        this.lock.lock();
    }

    public void unlock() {
        if (this.lock.isHeldByCurrentThread() && this.lock.isLocked()) {
            this.lock.unlock();
        }

    }


}
