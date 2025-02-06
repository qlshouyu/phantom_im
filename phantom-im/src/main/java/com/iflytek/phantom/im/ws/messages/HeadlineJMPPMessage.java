package com.iflytek.phantom.im.ws.messages;

import com.iflytek.phantom.im.domain.Jid;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;

import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
public class HeadlineJMPPMessage<T> extends AbstractJMPPMessage<T> {

    public HeadlineJMPPMessage(String id, String headlineType, List<String> tos, T body) {
//        this(id, headlineType, null, new Jid(to), body);
        super(id, "message", "headline@" + headlineType, null, tos, body);
    }

//    public HeadlineJMPPMessage(String id, String headlineType, Jid from, Jid to, T body) {
//        super(id, "message", "headline@" + headlineType, from, to, body);
//    }


}
