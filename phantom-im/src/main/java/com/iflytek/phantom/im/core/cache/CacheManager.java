package com.iflytek.phantom.im.core.cache;

import java.time.Duration;
import java.util.Set;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/4
 * @Version 1.0.0
 */
public interface CacheManager {

    void set(String key, Object obj, Duration timeout);

    Object get(String key);

    void delete(String key);


    void setAdd(String key, Object... obj);

    void setInit(String key, Object... obj);

    void setRemove(String key, Object... objs);

    Set<Object> setGet(String key);
}
