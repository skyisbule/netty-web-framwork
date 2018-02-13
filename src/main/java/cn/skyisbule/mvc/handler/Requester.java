package cn.skyisbule.mvc.handler;

import cn.skyisbule.mvc.http.SkyRequest;
import cn.skyisbule.mvc.http.SkyResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by skyisbule on 2018/2/12.
 */
@Slf4j
public class Requester implements Runnable{

    private final ChannelHandlerContext ctx;
    private final FullHttpRequest       req;

    public Requester(ChannelHandlerContext ctx, FullHttpRequest request){
        this.ctx=ctx;
        this.req=request;
    }

    @Override
    public void run(){

        SkyRequest request  = SkyRequest.build(req);
        SkyResponse response = SkyResponse.buildSelf(ctx);
        response.write();

    }

    public void writeFinish(){

    }
}
