package com.example.nettypractice.config;

import com.example.nettypractice.auth.AuthDecoder;
import com.example.nettypractice.auth.AuthEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.channel.group.DefaultChannelGroup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Slf4j
@Getter
@Setter
@Configuration
@RequiredArgsConstructor
public class AuthServerConfig {

    @Value("${auth.readTimeOut}")
    private int readTimeOut;

    @Value("${auth.bossCount}")
    private int bossCount;

    @Value("${auth.workerCount}")
    private int workerCount;

    @Value("${auth.host}")
    private String host;

    @Value("${auth.port}")
    private int port;


    private ChannelGroup allChannel = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private AuthDecoder authDecoder;

    private AuthEncoder authEncoder;

    private final AuthHandler authHandler;


    @Bean @Qualifier("authBootStrap")
    public ServerBootstrap informationBootStrap() {

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(infoBossGroup(), infoWorkerGroup())
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        makePipeLine(pipeline);
                    }
                });

        bootstrap.option(ChannelOption.SO_BACKLOG, 5);

        return bootstrap;

    }

    @Bean
    @Qualifier("infoBossCount")
    public NioEventLoopGroup infoBossGroup(){
        return new NioEventLoopGroup(bossCount);
    }

    @Bean @Qualifier("infoWorkerCount")
    public NioEventLoopGroup infoWorkerGroup(){
        return new NioEventLoopGroup(workerCount);
    }

    @Bean @Qualifier("infoInetSocketAddress")
    public InetSocketAddress infoInetSocketAddress() {
        return new InetSocketAddress(host, port);
    }

    private void makePipeLine(ChannelPipeline pipeline){
        authDecoder = new AuthDecoder();
        authEncoder = new AuthEncoder();
        pipeline.addLast(authDecoder);
        pipeline.addLast(authEncoder);
        pipeline.addLast(new ReadTimeoutHandler(readTimeOut));
        pipeline.addLast(authHandler);

    }
}
