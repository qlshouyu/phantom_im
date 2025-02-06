package com.iflytek.phantom.im.ws.messages;

import com.iflytek.phantom.im.domain.Jid;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
public class IQMessage<T> extends AbstractJMPPMessage<T> {

    public IQMessage(String id, String type, String from, T body) {
        this(id, type, new Jid(from), body);
    }

    public IQMessage(String id, String type, Jid from,  T body) {
        super(id, "iq", type, from, null, body);
    }

}
