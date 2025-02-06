package com.iflytek.phantom.im.core.cache;

import com.iflytek.phantom.im.utils.JSONUtil;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Set;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/4
 * @Version 1.0.0
 */
public class RedisCacheManager implements CacheManager {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisCacheManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void set(String key, Object obj, Duration timeout) {
        this.redisTemplate.opsForValue().set(key, JSONUtil.objToString(obj), timeout);
    }

    @Override
    public Object get(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        this.redisTemplate.delete(key);
    }

    @Override
    public void setAdd(String key, Object... obj) {
        if (obj != null && obj.length > 0) {
            this.redisTemplate.opsForSet().add(key, obj);
        }
    }

    @Override
    public void setInit(String key, Object... obj) {
        this.redisTemplate.delete(key);
        this.setAdd(key, obj);
    }

    @Override
    public void setRemove(String key, Object... objs) {
        this.redisTemplate.opsForSet().remove(key, objs);
    }

    @Override
    public Set<Object> setGet(String key) {
        return this.redisTemplate.opsForSet().members(key);
    }
}
