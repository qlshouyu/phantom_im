package com.iflytek.phantom.im.core.clusters;

import lombok.Getter;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/4
 * @Version 1.0.0
 */
public class PhantomIMServer {
    @Getter
    private String gate;

    private String id;

    @Getter
    private Boolean isSelf = false;


}
