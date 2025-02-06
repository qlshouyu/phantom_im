package com.iflytek.phantom.im.service;

import com.iflytek.phantom.im.core.User;
import com.iflytek.phantom.im.domain.Tag;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/4
 * @Version 1.0.0
 */
public class TagService {

    // key:tag
    private final static Map<String, Tag> TAGS;

    static {
        TAGS = new ConcurrentHashMap<>();
    }

    public void addTagUser(String tag, User user) {
        Tag tu = TAGS.computeIfAbsent(tag, tagKey -> new Tag(tagKey, new ReentrantLock()));
        try {
            tu.lock();
            tu.addUser(user);
        } finally {
            tu.unlock();
        }
    }


    public void delTag(String tag) {
        TAGS.remove(tag);
    }

    public void delTagUser(String tag, User user) {
        Tag tu = TAGS.get(tag);
        if (tu != null) {
            try {
                tu.lock();
                if (0 == tu.delUser(user)) {
                    this.delTag(tag);
                }
            } finally {
                tu.unlock();
            }
        }
    }

    public void addUserTag(User user, String tag) {
        this.addTagUser(tag, user);
    }

    public void addUserTags(User user, List<String> tags) {
        tags.forEach(tag -> {
            this.addUserTag(user, tag);
        });
    }

    public void delUserTag(User user, String tag) {
        Tag tagWrap = TAGS.get(tag);
        if (tagWrap != null) {
            tagWrap.delUser(user);
        }
    }

    public void setUserTags(User user, Set<String> oldTags, List<String> tags) {
        // User`s old tags
        Map<String, String> newTags = new HashMap<>();
        tags.forEach(tag -> {
            newTags.put(tag, tag);
        });
        // Delete the no used
        Map<String, String> oldTagMaps = new HashMap<>();
        oldTags.forEach(oldTag -> {
            oldTagMaps.put(oldTag, oldTag);
            if (!newTags.containsKey(oldTag)) {
                this.delUserTag(user, oldTag);
            }
        });

        // Add the not exist
        tags.forEach(newTag -> {
            if (!oldTagMaps.containsKey(newTag)) {
                this.addUserTag(user, newTag);
            }
        });

    }

    public Collection<User> getUsers(String tag) {
        Tag tu = TAGS.get(tag);
        if (tu != null) {
            return tu.getUsers();
        } else {
            return Collections.emptyList();
        }
    }


}
