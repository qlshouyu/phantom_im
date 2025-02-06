package com.iflytek.phantom.im.ws;

import com.iflytek.phantom.im.core.UserManager;
import com.iflytek.phantom.im.ws.handler.AbstractHandler;
import com.iflytek.phantom.im.ws.handler.ComposeHandler;
import com.iflytek.phantom.im.ws.handler.iq.IQSetHandler;
import com.iflytek.phantom.im.ws.handler.presence.PingHandler;
import com.iflytek.phantom.im.ws.handler.stream.StartHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.List;


@Configuration
public class WebSocketConfiguration {
    @Bean
    public HandlerMapping webSocketMapping(WebSocketHandler handler) {
        return new SimpleUrlHandlerMapping() {{
            setOrder(Ordered.HIGHEST_PRECEDENCE);
            setUrlMap(new HashMap<String, WebSocketHandler>() {{
                put("phantom_im", handler);
            }});
        }};
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public PingHandler pingHandler() {
        return new PingHandler();
    }


    @Bean
    public StartHandler startHandler() {
        return new StartHandler();
    }

    @Bean
    public IQSetHandler iqSetHandler() {
        return new IQSetHandler();
    }

    @Bean
    public ComposeHandler composeHandler(List<AbstractHandler> handlers) {
        return new ComposeHandler(handlers);
    }

    @Bean
    public WebSocketHandler wsHandler(UserManager userManager, ComposeHandler composeHandler) {
        WebSocketHandler socketHandler = new WebsocketTransport(userManager, composeHandler);
        return socketHandler;
    }

}
