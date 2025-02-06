package com.iflytek.phantom.im.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@Data
public class PushVo {
    private String body;
    // 推送类型 t,c
    private String type;
    private List<String> to;

}
