package com.iflytek.phantom.im.controller;

import com.iflytek.phantom.im.core.engine.IMEngineProducerPool;
import com.iflytek.phantom.im.domain.vo.PushVo;
import com.iflytek.phantom.im.ws.messages.HeadlineJMPPMessage;
import com.qlshouyu.jframework.web.AbstractController;
import com.qlshouyu.jframework.web.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.iflytek.phantom.im.utils.Constants;

import java.util.List;
import java.util.UUID;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/5
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/phantom_im/api/v1/push")
public class PushController extends AbstractController {
    @Autowired
    private IMEngineProducerPool producerPool;

    @PostMapping
    public Mono<ResponseResult<String>> push(@RequestBody PushVo data) {
        return Mono.just(data).doOnNext(d -> {
            //String id, String headlineType,T body, String... tos
            String pushToPrefix = "@";
            if ("tag".equals(data.getType())) {
                pushToPrefix = "t" + pushToPrefix;
            } else if ("jid".equals(data.getType())) {
                pushToPrefix = "c" + pushToPrefix;
            }
            final String fpushToPrefix = pushToPrefix;
            HeadlineJMPPMessage msg = new HeadlineJMPPMessage(
                    UUID.randomUUID().toString().replace("-", ""),
                    data.getTo().stream().map(to -> fpushToPrefix + to).toList(),
                    data.getBody()
            );
            try {
                producerPool.get(Constants.PoolContentType.PUSH).producer(msg);
            } catch (Exception e) {
                Mono.error(e);
            }
        }).map(p -> new ResponseResult<>("成功"));
    }

    @PostMapping("/multi")
    public Mono<ResponseResult<String>> push(@RequestBody List<PushVo> data) {

        // 使用Flux处理每个元素
        return Flux.fromIterable(data)
                .map(item -> {
                    String pushToPrefix = "@";
                    if ("tag".equals(item.getType())) {
                        pushToPrefix = "t" + pushToPrefix;
                    } else if ("jid".equals(item.getType())) {
                        pushToPrefix = "c" + pushToPrefix;
                    }
                    final String fpushToPrefix = pushToPrefix;
                    HeadlineJMPPMessage msg = new HeadlineJMPPMessage(
                            UUID.randomUUID().toString().replace("-", ""),
                            item.getTo().stream().map(to -> fpushToPrefix + to).toList(),
                            item.getBody()
                    );
                    try {
                        producerPool.get(Constants.PoolContentType.PUSH).producer(msg);
                    } catch (Exception e) {
                        Mono.error(e);
                    }
                    return item;
                })
                .then().map(p -> new ResponseResult<>("成功"));
    }
}
