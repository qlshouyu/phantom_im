package com.iflytek.phantom.im.ws.handler;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/6
 * @Version 1.0.0
 */
public abstract class StreamHandler<T> extends AbstractHandler<T> {


    @Override
    public String category() {
        return "stream";
    }
}
