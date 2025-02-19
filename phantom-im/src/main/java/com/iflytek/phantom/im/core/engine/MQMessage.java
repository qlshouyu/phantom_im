package com.iflytek.phantom.im.core.engine;

import com.iflytek.phantom.im.utils.JSONUtil;
import com.iflytek.phantom.im.ws.AbstractJMPPMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/14
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MQMessage {
    private String topic;
    private byte[] body;
    private Map<String, String> properties;

    public AbstractJMPPMessage getObjBody() {
        return AbstractJMPPMessage.fromString(new String(body));
    }

    @Override
    public String toString() {
        return JSONUtil.objToString(this);
    }
}
