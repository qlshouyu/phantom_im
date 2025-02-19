package com.iflytek.phantom.im.ws;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.iflytek.phantom.im.domain.Jid;
import com.iflytek.phantom.im.utils.JSONUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@NoArgsConstructor
public class AbstractJMPPMessage {

    public AbstractJMPPMessage(String id, MESSAGE_CATEGORY category, MESSAGE_TYPE type, Jid from, List<String> tos, Object body) {
        this.id = id;
        this.c = category.value;
        this.type = type.value;
        this.from = from;
        this.setTo(tos);
        this.body = body;
    }
//    public AbstractJMPPMessage(String id, MESSAGE_CATEGORY category, MESSAGE_TYPE type, Jid from, List<Jid> tos, Object body) {
//        this.id = id;
//        this.c = category.value;
//        this.type = type.value;
//        this.from = from;
//        this.tos=tos;
//        this.body = body;
//    }

    @Getter
    @Setter
    protected String id;

    @Getter
    @Setter
    protected String c;

    @Getter
    @Setter
    protected String type;

    protected Jid from;

    protected List<Jid> tos;
    @Getter
    @Setter
    protected Object body;


    public String getFrom() {
        return from.getJid();
    }

    public void setFrom(String from) {
        this.from = new Jid(from);
    }

    public List<String> getTo() {
        return this.tos.stream().map(Jid::getJid).toList();
    }

    public void setTo(List<String> to) {
        if (this.tos == null) {
            this.tos = new ArrayList<>();
        }
        to.forEach(j -> {
            this.tos.add(new Jid(j));
        });
    }

    public static AbstractJMPPMessage fromString(String message) {
        return JSONUtil.stringToObject(message, AbstractJMPPMessage.class);
    }

    @Override
    public String toString() {
        return JSONUtil.objToString(this);
    }


    @JsonIgnore
    public Jid getFROM() {
        return this.from;
    }

    @JsonIgnore
    public List<Jid> getTOS() {
        return this.tos;
    }

    @JsonIgnore
    public <T> T getObjectBody(Class<T> clazz) {
        return JSONUtil.objToObject(this.body, clazz);
    }

    @JsonIgnore
    public <T> T getObjectBody(TypeReference<T> valueTypeRef) {
        return JSONUtil.objToObject(this.body, valueTypeRef);
    }


    @JsonIgnore
    public MESSAGE_CATEGORY getENCategory() {
        return MESSAGE_CATEGORY.valueOf(this.c);
    }

    @JsonIgnore
    public MESSAGE_TYPE getENType() throws IllegalArgumentException {
        return MESSAGE_TYPE.valueOf(this.type);
    }


    public enum MESSAGE_CATEGORY {
        message("message"),
        iq("iq"),
        presence("presence");

        private String value;

        MESSAGE_CATEGORY(String value) {
            this.value = value;
        }

    }


    public enum MESSAGE_TYPE {
        // Message type
        chat("chat"),
        groupchat("groupchat"),
        headline("headline"),
        normal("normal"),

        ack("ack"),

        // IQ type
        set("set"),
        add("add"),
        get("get"),
        // Persence type
        pong("pong"),
        ping("ping");
        private String value;

        MESSAGE_TYPE(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
