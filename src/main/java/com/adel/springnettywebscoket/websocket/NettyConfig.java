package com.adel.springnettywebscoket.websocket;

import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Adel.Albediwy
 */
@Configuration
public class NettyConfig {

    @Bean
    public HttpServerCodec httpServerCodec() {
        return new HttpServerCodec();
    }

    @Bean
    public HttpObjectAggregator httpObjectAggregator() {
        return new HttpObjectAggregator(65536);
    }

    @Bean
    public ChunkedWriteHandler chunkedWriteHandler() {
        return new ChunkedWriteHandler();
    }

    @Bean
    public WebSocketServerProtocolHandler webSocketServerProtocolHandler() {
        return new WebSocketServerProtocolHandler("/ws");
    }

    @Bean
    public Integer websocketPort(@Value("${websocket.port}") final Integer port) {
        return port;
    }

    @Bean
    public WebSocketServerCompressionHandler webSocketServerCompressionHandler() {
        return new WebSocketServerCompressionHandler();
    }

}
