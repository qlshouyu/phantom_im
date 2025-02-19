package com.iflytek.phantom.im.ws.messages;

import com.iflytek.phantom.im.domain.Jid;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
public class IQMessage extends AbstractJMPPMessage {


    public IQMessage(String id, MESSAGE_TYPE type, Jid from,  Object body) {
        super(id, MESSAGE_CATEGORY.iq, type, from, null, body);
    }

}
