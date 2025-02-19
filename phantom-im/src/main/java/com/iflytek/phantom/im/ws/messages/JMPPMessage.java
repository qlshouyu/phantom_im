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
public class JMPPMessage extends AbstractJMPPMessage {

    public JMPPMessage(String id, MESSAGE_TYPE type, String from, String to, Object body) {
        this(id, type, new Jid(from), new Jid(to), body);
    }

    public JMPPMessage(String id, MESSAGE_TYPE type, Jid from, Jid to, Object body) {
        super(id, MESSAGE_CATEGORY.message, type, from, Arrays.asList(to.getJid()), body);
    }

}
