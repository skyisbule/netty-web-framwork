package cn.skyisbule.server.netty;

import cn.skyisbule.config.Environment;
import cn.skyisbule.server.Server;
import cn.skyisbule.sky;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
/**
 * Created by skyisbule on 2018/2/12.
 * httpserver的netty实现
 */
@Slf4j
public class HttpServer implements Server{

    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private Channel        channel;
    private Environment    environment;

    @Override
    public void start(sky sky) throws Exception{


    }

    @Override
    public void stop() {

    }

    private void startServer() throws Exception{

        ServerBootstrap b = new ServerBootstrap();
        b.option(ChannelOption.SO_REUSEADDR,true);
        b.childOption(ChannelOption.SO_REUSEADDR,true);

        this.bossGroup = new NioEventLoopGroup();
        this.workGroup = new NioEventLoopGroup();
        b.group(bossGroup,workGroup);

        b.handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new HttpServerInitializer());

        String address = environment.getAddress();
        int prot = environment.getPort();

        channel = b.bind(address,prot)
                .sync()
                .channel();
    }

}
