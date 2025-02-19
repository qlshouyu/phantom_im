package com.iflytek.phantom.im.ws.messages;

import com.iflytek.phantom.im.ws.AbstractJMPPMessage;

import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
public class AckJMPPMessage extends AbstractJMPPMessage {

    public AckJMPPMessage(String id, MESSAGE_CATEGORY category, List<String> tos, Object body) {
        super(id, category, MESSAGE_TYPE.ack, null, tos, body);
    }

    public static AckJMPPMessage buildSuccess(String id, MESSAGE_CATEGORY category, String to) {
        return new AckJMPPMessage(id, category, Arrays.asList(to), new AckBody(200, "success"));
    }

    public static AckJMPPMessage buildFailed(String id, MESSAGE_CATEGORY category, String to, String errorTip) {
        return new AckJMPPMessage(id, category, Arrays.asList(to), new AckBody(500, errorTip));
    }


}
