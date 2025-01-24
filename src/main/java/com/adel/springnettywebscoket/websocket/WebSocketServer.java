package com.adel.springnettywebscoket.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * @author Adel.Albediwy
 */
@Service
public class WebSocketServer implements CommandLineRunner {
    private static final Logger log = LogManager.getLogger(WebSocketServer.class);

    private final Integer websocketPort;
    private final WebSocketServerInitializer webSocketServerInitializer;

    public WebSocketServer(Integer websocketPort,
                           WebSocketServerInitializer webSocketServerInitializer) {
        this.websocketPort = websocketPort;
        this.webSocketServerInitializer = webSocketServerInitializer;
    }

    @Override
    public void run(final String... args) throws Exception {
        final NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        final NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            final ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(webSocketServerInitializer);

            final ChannelFuture channelFuture = serverBootstrap.bind(websocketPort).sync();
            log.info("Websocket server started on port: {}", websocketPort);
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

        log.info("Shutting down websocket server on port: {}", websocketPort);
    }
}
