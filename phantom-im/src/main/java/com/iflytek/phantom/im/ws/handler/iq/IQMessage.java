package com.iflytek.phantom.im.ws.handler.iq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/6
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class IQMessage<T> implements Serializable {

    // tag,user
    protected String name;
    protected T body;
}
