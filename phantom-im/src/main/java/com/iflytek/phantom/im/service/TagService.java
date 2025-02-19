
package com.iflytek.phantom.im.service;

import com.iflytek.phantom.im.core.User;
import com.iflytek.phantom.im.domain.Tag;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/4
 * @Version 1.0.0
 */
@Component
public class TagService {

    // key:tag
    private final static Map<String, Tag> TAGS;

    static {
        TAGS = new ConcurrentHashMap<>();
    }

    /**
     * 向指定标签添加用户
     * 如果标签不存在，则创建新标签并添加用户
     *
     * @param tag  标签名称
     * @param user 用户对象
     */
    public void addTagUser(String tag, User user) {
        Tag tu = TAGS.computeIfAbsent(tag, tagKey -> new Tag(tagKey, new ReentrantLock()));
        try {
            tu.lock();
            tu.addUser(user);
        } finally {
            tu.unlock();
        }
    }

    /**
     * 删除指定标签
     *
     * @param tag 标签名称
     */
    public void delTag(String tag) {
        TAGS.remove(tag);
    }

    /**
     * 根据标签获取用户集合
     *
     * @param tag 标签名称
     * @return 与标签关联的用户集合
     */
    public Collection<User> getUserByTag(String tag) {
        return TAGS.get(tag).getUsers();
    }

    /**
     * 根据标签列表获取用户集合
     * 该方法通过遍历提供的标签列表，从缓存中获取与标签关联的用户，并合并这些用户到一个集合中返回
     * 如果没有找到任何用户，返回空集合
     *
     * @param tags 标签列表，用于查找相关的用户
     * @return 包含找到的用户的集合，如果未找到则返回空集合
     */
    public Set<User> getUserByTags(List<String> tags) {
        Set<User> users = new HashSet<>();
        tags.forEach(targetTag -> {
            Tag cacheTag = TAGS.get(targetTag);
            if (cacheTag != null && cacheTag.getUsers().size() > 0) {
                users.addAll(cacheTag.getUsers());
            }
        });
        if (users.isEmpty()) {
            return Collections.emptySet();
        }
        return users;
    }

    /**
     * 从指定标签中删除用户
     * 如果标签中没有其他用户，则删除该标签
     *
     * @param tag  标签名称
     * @param user 用户对象
     */
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

    /**
     * 向用户添加指定标签
     * 如果标签不存在，则创建新标签并添加用户
     *
     * @param user 用户对象
     * @param tag  标签名称
     */
    public void addUserTag(User user, String tag) {
        this.addTagUser(tag, user);
    }

    /**
     * 向用户添加多个标签
     * 如果标签不存在，则创建新标签并添加用户
     *
     * @param user 用户对象
     * @param tags 标签列表
     */
    public void addUserTags(User user, List<String> tags) {
        tags.forEach(tag -> {
            this.addUserTag(user, tag);
        });
    }

    /**
     * 从用户中删除指定标签
     *
     * @param user 用户对象
     * @param tag  标签名称
     */
    public void delUserTag(User user, String tag) {
        Tag tagWrap = TAGS.get(tag);
        if (tagWrap != null) {
            tagWrap.delUser(user);
        }
    }

    /**
     * 设置用户的标签
     * 删除用户不再拥有的标签，添加用户新拥有的标签
     *
     * @param user     用户对象
     * @param oldTags  用户当前拥有的标签集合
     * @param tags     用户需要拥有的标签列表
     */
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

    /**
     * 根据标签获取用户集合
     *
     * @param tag 标签名称
     * @return 与标签关联的用户集合，如果标签不存在则返回空列表
     */
    public Collection<User> getUsers(String tag) {
        Tag tu = TAGS.get(tag);
        if (tu != null) {
            return tu.getUsers();
        } else {
            return Collections.emptyList();
        }
    }

}