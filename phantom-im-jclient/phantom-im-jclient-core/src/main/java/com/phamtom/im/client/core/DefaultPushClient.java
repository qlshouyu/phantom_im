package com.phamtom.im.client.core;

import com.phamtom.im.client.core.model.PushRequest;
import com.phamtom.im.client.core.model.PushResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/20
 * @Version 1.0.0
 */
@Slf4j
public class DefaultPushClient implements PushClient {
    private RestClient restClient;
    private String baseUrl;

    public DefaultPushClient(String baseUrl) {
        this(null, baseUrl);
    }

    public DefaultPushClient(RestClient restClient, String baseUrl) {
        if (restClient == null) {
            log.info("RestClient init");
            SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
            httpRequestFactory.setReadTimeout(120000);
            httpRequestFactory.setConnectTimeout(3000);
            restClient = RestClient.create(new RestTemplate(httpRequestFactory));
        }
        this.restClient = restClient;
        this.baseUrl = baseUrl;

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
        // post 请求API_PUSH_MULTI并且获取JSON结果转对象
        try {
            ResponseEntity<String> responseEntity = this.restClient.post().uri(this.baseUrl + url)
                    .body(body)
                    .retrieve()
                    .toEntity(String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return PushResponse.fromJson(responseEntity.getBody());
            } else {
                return new PushResponse(responseEntity.getStatusCode().value(), responseEntity.getBody(), null);
            }
        } catch (Exception e) {
            log.error("Failed to request {} url:{},body:{}", e.getMessage(), this.baseUrl + url, body);
            return new PushResponse(500, "Failed to request:" + e.getMessage(), null);
        }
    }
}
