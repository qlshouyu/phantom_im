package com.iflytek.phantom.im.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: Phantom Bee
 * @create: 2024/12/27
 * @Version 1.0.0
 */
@Slf4j
public class WSQuery {

    private Map<String, String> queryMap;

    public WSQuery(URI uri) {
        this.queryMap = new HashMap<>();
        String query = uri.getQuery();
        //有参数
        String[] params = query.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (log.isDebugEnabled()) {
                log.debug("query:{}={}", keyValue[0], keyValue[1]);
            }
            this.queryMap.put(keyValue[0], keyValue[1]);
        }
    }

    public String get(String name) {
        return this.queryMap.get(name);
    }
}
