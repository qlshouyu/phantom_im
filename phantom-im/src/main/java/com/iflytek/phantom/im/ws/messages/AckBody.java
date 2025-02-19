package com.iflytek.phantom.im.ws.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/18
 * @Version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AckBody {

    private Integer code;
    private String tip;
}
