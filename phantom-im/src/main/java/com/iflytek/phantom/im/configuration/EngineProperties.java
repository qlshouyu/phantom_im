package com.iflytek.phantom.im.configuration;

import com.iflytek.phantom.im.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@Data
@AllArgsConstructor
public class EngineProperties {
    private String addr;
    private String tenant;
    private String app;
    private List<PoolContentType> poolContentTypes;

    public EngineProperties() {
        this.poolContentTypes = new ArrayList<>();
        this.poolContentTypes.add(new PoolContentType(Constants.PoolContentType.PUSH.getValue(), 1));
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PoolContentType {
        private String name;
        private Integer size = 1;
    }
}
