package cn.skyisbule.mvc.handler;

import cn.skyisbule.config.Environment;
import cn.skyisbule.ioc.bean.BeanFactory;
import cn.skyisbule.mvc.http.SkyRequest;
import cn.skyisbule.mvc.http.SkyResponse;
import cn.skyisbule.mvc.router.RouteHandle;
import cn.skyisbule.mvc.router.Router;
import cn.hutool.core.util.ReflectUtil;
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
    Router router = Environment.router;

    public Requester(ChannelHandlerContext ctx, FullHttpRequest request){
        this.ctx=ctx;
        this.req=request;
    }

    @Override
    public void run(){
        //首先构造两个http类 用作处理
        SkyRequest request   = SkyRequest.build(req);
        SkyResponse response = SkyResponse.buildSelf(ctx);

        String url = request.getUri();
        log.info("收到请求：{}",url);

        //拿到开发者类
        RouteHandle handle= router.getHandler(url);

        Object result = ReflectUtil.invoke(BeanFactory.getObjByUrl(url),handle.getMethod());

        response.setContent(result.toString());

        response.write();

    }

    public void writeFinish(){
        ctx.close();
    }
}
