package cn.skyisbule.server.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by skyisbule on 2018/2/12.
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

        p.addLast(new HttpServerCodec(36192 * 2, 36192 * 8, 36192 * 16, false));
        p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
        p.addLast(new ChunkedWriteHandler());
        p.addLast(new HttpServerHandler());

    }

}
