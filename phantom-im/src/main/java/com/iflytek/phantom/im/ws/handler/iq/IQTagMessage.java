package com.iflytek.phantom.im.ws.handler.iq;

import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/6
 * @Version 1.0.0
 */
public class IQTagMessage extends IQMessage<List<String>> {
    public IQTagMessage(String... tags) {
        super("tag", Arrays.asList(tags));
    }
}

