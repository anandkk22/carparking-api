package com.parking.springboot_parking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.parking.springboot_parking.handlers.SocketConnectionHandler;

@Configuration 
@EnableWebSocket 
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new SocketConnectionHandler(), "/api/v1").setAllowedOrigins(("*"));
        // throw new UnsupportedOperationException("Unimplemented method 'registerWebSocketHandlers'");
    }

}
