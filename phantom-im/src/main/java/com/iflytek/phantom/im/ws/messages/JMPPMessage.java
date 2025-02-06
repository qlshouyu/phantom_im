package com.iflytek.phantom.im.ws.messages;

import com.iflytek.phantom.im.domain.Jid;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;

import java.util.Arrays;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
public class JMPPMessage<T> extends AbstractJMPPMessage<T> {

    public JMPPMessage(String id, String type, String from, String to, T body) {
        this(id, type, new Jid(from), new Jid(to), body);
    }

    public JMPPMessage(String id, String type, Jid from, Jid to, T body) {
        super(id, "message", type, from, Arrays.asList(to), body);
    }

}
