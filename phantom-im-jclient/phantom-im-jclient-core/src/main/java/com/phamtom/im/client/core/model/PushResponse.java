package com.phamtom.im.client.core.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushResponse {
    private Integer code;
    private String tip;
    private Object data;

    // 将给定的字符串转成此对象
    public static PushResponse fromJson(String json) throws RuntimeException {
        try {
            return PushRequest.JSON_MAPPER.readValue(json, PushResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
