package com.iflytek.phantom.im.utils;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/4
 * @Version 1.0.0
 */
public class Constants {
    public static String GPREFIX = "ph_im::";

    public static String PREFIX_TAG_USER = GPREFIX + "{tag_users}::";

    public enum PoolContentType {
        PUSH("push"),
        IM_PEER("im_peer"),
        IM_GROUP("im_group");

        private final String value;

        PoolContentType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

    }
}
