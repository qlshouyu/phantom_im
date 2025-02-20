package com.phamtom.im.client.core;

import com.phamtom.im.client.core.model.PushRequest;
import com.phamtom.im.client.core.model.PushResponse;

import java.util.List;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/20
 * @Version 1.0.0
 */
public interface PushClient {

    PushResponse push(PushRequest msg);


    PushResponse pushMulti(List<PushRequest> msg);
}
