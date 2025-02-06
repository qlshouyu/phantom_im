package com.iflytek.phantom.im.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JSONUtil {

    private final static ObjectMapper OM;

    static {
        OM = new ObjectMapper();
        OM.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        OM.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OM.enable(SerializationFeature.WRAP_EXCEPTIONS);
        OM.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String objToString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else {
            try {
                return OM.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                log.error("Object been parsed error", e);
            }
        }
        return null;
    }


    public static <T> T objToObject(Object content, TypeReference<T> valueTypeRef) {
        try {
            String str = OM.writeValueAsString(content);
            return OM.readValue(str, valueTypeRef);
        } catch (JsonProcessingException e) {
            log.error("Object been parsed error", e);
        }
        return null;
    }

    public static <T> T objToObject(Object content, Class<T> tClass) {
        try {
            String str = OM.writeValueAsString(content);
            return OM.readValue(str, tClass);
        } catch (JsonProcessingException e) {
            log.error("Object been parsed error", e);
        }
        return null;
    }

    public static <T> T stringToObject(String content, Class<T> tClass) {
        try {
            return OM.readValue(content, tClass);
        } catch (JsonProcessingException e) {
            log.error("String to object error:{}", content, e);
        }
        return null;
    }

    public static <T> T stringToObject(String content, TypeReference<T> valueTypeRef) {
        try {
            return OM.readValue(content, valueTypeRef);
        } catch (JsonProcessingException e) {
            log.error("String to object error:{}", content, e);
        }
        return null;
    }

}
