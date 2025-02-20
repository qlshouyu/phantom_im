package com.phamtom.im.client.core;

import com.phamtom.im.client.core.model.PushRequest;
import com.phamtom.im.client.core.model.PushResponse;
import org.apache.hc.client5.http.classic.HttpClient;

import java.util.List;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/20
 * @Version 1.0.0
 */
public class ApacheClientPushClient implements PushClient {
    private HttpClient httpClient;

    public ApacheClientPushClient(String baseUrl) {

    }

    public ApacheClientPushClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public PushResponse push(PushRequest msg) {
        return send(PhiConstants.API_PUSH, msg);
    }

    @Override
    public PushResponse pushMulti(List<PushRequest> msg) {
        return send(PhiConstants.API_PUSH_MULTI, msg);

    }

    private PushResponse send(String url, Object body) {
        return null;
    }
}
