package com.iflytek.phantom.im.ws.messages;

import com.iflytek.phantom.im.domain.Jid;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
public class PresencePingMessage<T> extends AbstractJMPPMessage<T> {

    public PresencePingMessage(String id, String from, T body) {
        this(id, new Jid(from), body);
    }

    public PresencePingMessage(String id, Jid from, T body) {
        super(id, "presence", "ping", from, null, body);
    }

}
