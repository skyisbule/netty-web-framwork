package cn.skyisbule.mvc.handler;

import cn.skyisbule.mvc.http.SkyRequest;
import cn.skyisbule.mvc.http.SkyResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by skyisbule on 2018/2/12.
 * 当netty创建这个类的对象时就意味着netty已经完成了他的工作，
 * netty将解码后的httpRequest对象和通讯上下文递交给此类的实例，
 * 此类对Request进行处理，生成Response对象后返回给客户端，断开连接。
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

        SkyRequest request   = SkyRequest.build(req);
        SkyResponse response = SkyResponse.buildSelf(ctx);
        response.write();

    }

    public void writeFinish(){
        ctx.close();
    }
}
