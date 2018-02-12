package cn.skyisbule.server.netty;

import cn.skyisbule.config.Environment;
import cn.skyisbule.server.Server;
import cn.skyisbule.sky;
import cn.skyisbule.util.SkyThreadFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * Created by skyisbule on 2018/2/12.
 * httpserver的netty实现
 */
@Slf4j
@NoArgsConstructor
public class HttpServer implements Server{

    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private Channel        channel;
    private Environment    environment;

    private int boosThreadsCount;
    private int workThreadsCount;

    @Override
    public void start(sky sky) throws Exception{

        this.environment = sky.getEnvironment();
        boosThreadsCount=environment.getBossThreadCount();
        workThreadsCount=environment.getWorkThreadCount();

        this.startServer();

    }

    @Override
    public void stop() {

    }


    public void startServer(){
        EventLoopGroup bossGroup = new NioEventLoopGroup(boosThreadsCount,new SkyThreadFactory("boos"));
        EventLoopGroup workerGroup = new NioEventLoopGroup(workThreadsCount,new SkyThreadFactory("work"));
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpServerInitializer());

            ChannelFuture f = b
                    .bind(environment.getPort())
                    .sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
