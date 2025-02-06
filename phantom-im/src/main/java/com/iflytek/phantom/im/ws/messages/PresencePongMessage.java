package com.iflytek.phantom.im.ws.messages;

import com.iflytek.phantom.im.domain.Jid;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;
import org.apache.commons.lang3.StringUtils;

/**
 * @description:
 * @author: Phantom Bee
 * @create: 2024/12/27
 * @Version 1.0.0
 */
public class PresencePongMessage extends AbstractJMPPMessage<String> {

    private static String strBody;

//    public PresencePongMessage(String id) {
//        this(id, new Jid(from));
//    }

    public PresencePongMessage(String id) {
        super(id, "presence", "pong", (String) null, null, null);
    }


    @Override
    public String toString() {
        if (StringUtils.isEmpty(this.strBody)) {
            this.strBody = super.toString();
        }
        return this.strBody;
    }

}
