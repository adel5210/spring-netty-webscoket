package com.adel.springnettywebscoket.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Adel.Albediwy
 */
@Service
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;
    private final HttpServerCodec httpServerCodec;
    private final HttpObjectAggregator httpObjectAggregator;
    private final ChunkedWriteHandler chunkedWriteHandler;
    private final WebSocketServerProtocolHandler webSocketServerProtocolHandler;
    private final WebSocketFrameHandler webSocketFrameHandler;

    public WebSocketServerInitializer(HttpServerCodec httpServerCodec,
                                      HttpObjectAggregator httpObjectAggregator,
                                      ChunkedWriteHandler chunkedWriteHandler,
                                      WebSocketServerProtocolHandler webSocketServerProtocolHandler,
                                      WebSocketFrameHandler webSocketFrameHandler) {
        this.sslCtx = null;
        this.httpServerCodec = httpServerCodec;
        this.httpObjectAggregator = httpObjectAggregator;
        this.chunkedWriteHandler = chunkedWriteHandler;
        this.webSocketServerProtocolHandler = webSocketServerProtocolHandler;
        this.webSocketFrameHandler = webSocketFrameHandler;
    }

    @Override
    protected void initChannel(final SocketChannel socketChannel) throws Exception {
        final ChannelPipeline pipeline = socketChannel.pipeline();
        if(null != sslCtx){
            pipeline.addLast(sslCtx.newHandler(socketChannel.alloc()));
        }
        pipeline.addLast(httpServerCodec);
        pipeline.addLast(httpObjectAggregator);
        pipeline.addLast(chunkedWriteHandler);
        pipeline.addLast(webSocketServerProtocolHandler);
        pipeline.addLast(webSocketFrameHandler);
    }
}
