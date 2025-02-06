package com.iflytek.phantom.im.ws;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iflytek.phantom.im.domain.Jid;
import com.iflytek.phantom.im.utils.JSONUtil;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractJMPPMessage<T> {

    public AbstractJMPPMessage(String id, String c, String type, String from, List<String> tos, T body) {
        this(id, c, type, new Jid(from), null, body);
        this.setTo(tos);
    }

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
    protected T body;


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
}
