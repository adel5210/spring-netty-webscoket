package com.adel.springnettywebscoket.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author Adel.Albediwy
 */
@Service
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final Logger log = LogManager.getLogger(WebSocketFrameHandler.class);

    @Override
    protected void channelRead0(final ChannelHandlerContext channelHandlerContext,
                                final TextWebSocketFrame textWebSocketFrame) throws Exception {
        final String request = textWebSocketFrame.text();
        log.info("Received: {}", request);
        channelHandlerContext.writeAndFlush(new TextWebSocketFrame("Hello, " + request + "!"));
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx,
                                final Throwable cause) throws Exception {
        log.error("Error: {}", cause.getMessage());
        ctx.close();
    }
}
