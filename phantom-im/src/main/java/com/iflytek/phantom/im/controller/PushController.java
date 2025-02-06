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
import reactor.core.publisher.Mono;

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
    public Mono<ResponseResult> push(@RequestBody PushVo data) {
        return Mono.just(data).doOnNext(d -> {
            //String id, String headlineType,T body, String... tos
            HeadlineJMPPMessage<String> msg = new HeadlineJMPPMessage(
                    UUID.randomUUID().toString().replace("-", ""),
                    data.getType(),
                    data.getTo(),
                    data.getBody()
                    );
            try {
                producerPool.get().producer(msg);
            } catch (Exception e) {
                Mono.error(e);
            }
        }).map(p -> new ResponseResult<>());
    }
}
