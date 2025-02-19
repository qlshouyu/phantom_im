package com.iflytek.phantom.im.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@AllArgsConstructor
public class Jid {
    private String jid;

    public Jid(String jid) {
        this.setJid(jid);
    }

    @Getter
    @JsonIgnore
    private String eid;
    @Getter
    @JsonIgnore
    private String domain;
    @Getter
    @JsonIgnore
    private String clientType;

    public String getJid() {
        return this.toString();
    }

    public void setJid(String jid) {
        if (jid != null) {
            this.jid = jid;
            String[] jids = jid.split("@");
            if (jids.length == 2) {
                this.eid = jids[0];
                String end = jids[1];
                if (StringUtils.isNotBlank(end)) {
                    String[] ends = jids[1].split("/");
                    this.domain = ends[0];
                    if (ends.length == 2) {
                        this.clientType = ends[1];
                    }
                }
            } else {
                this.eid = jids[0];
            }
        }

    }

    @Override
    public String toString() {
        if (StringUtils.isEmpty(this.jid)) {
            if (StringUtils.isNotEmpty(this.eid)) {
                this.jid += this.eid;
            }
            if (StringUtils.isNotEmpty(this.domain)) {
                this.jid += "@" + this.domain;
            }
            if (StringUtils.isNotEmpty(this.clientType)) {
                this.jid += "/" + this.clientType;
            }
            if (this.jid.startsWith("@") || this.jid.startsWith("/")) {
                this.jid.substring(0, 1);
            }
        }
        return this.jid;
    }
}
