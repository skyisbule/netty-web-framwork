package cn.skyisbule.mvc.handler;

import cn.skyisbule.config.Environment;
import cn.skyisbule.ioc.annotation.Json;
import cn.skyisbule.ioc.bean.BeanFactory;
import cn.skyisbule.mvc.binding.DataBinder;
import cn.skyisbule.mvc.http.SkyRequest;
import cn.skyisbule.mvc.http.SkyResponse;
import cn.skyisbule.mvc.router.RouteHandle;
import cn.skyisbule.mvc.router.Router;
import cn.hutool.core.util.ReflectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

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
    Router      router    =  Environment.router;
    DataBinder  binder    =  new DataBinder();
    Object      urlObj    =  null;
    Method      urlMethod =  null;

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

        //拿到开发者类(@Url那个)
        RouteHandle handle= router.getHandler(url);
        //当前路由的对象
        urlObj = BeanFactory.getObjByUrl(url);
        //当前路由对应的方法
        urlMethod = handle.getMethod();
        //如果是404
        if (handle.is404){
            deal404(response);
            writeFinish();
            return;
        }
        //todo 这里要判断一下返回类型是否为static类型 即静态文件

        //最终返回给客户端的东西
        Object result = "no data from server";
        //尝试通过反射获取用户返回值
        try {
            //方法是void的判定放在json()里
                Object[] args = binder.getResult(request, urlObj, urlMethod);
                result = ReflectUtil.invoke(BeanFactory.getObjByUrl(url), handle.getMethod(), args);
                result = json(result);
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(500);
            result = "服务端发生错误，请查看控制台信息。";
        }

        //设置返回消息
        response.setContent(result.toString());
        //返回
        response.write();
        //结束
        writeFinish();
    }

    private void deal404(SkyResponse response){
        response.setStatus(404);
        response.setContent("404 not found");
        response.write();
    }

    private String json(Object res){
        if (res == null)
            return "no data from server";
        if (urlObj.getClass().isAnnotationPresent(Json.class)||
                urlMethod.isAnnotationPresent(Json.class)){
            ObjectMapper mapper = new ObjectMapper();
            try {
                res = mapper.writeValueAsString(res);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return res.toString();
    }


    private void fileTest(SkyResponse response){
        File file = new File("C:\\Users\\ZDNF\\Desktop\\timg.jpg");
        response.addHeader("Content-Length",file.length());
        response.write();
        byte[] buf = new byte[1024];
        BufferedInputStream bis = null;
        try{
            bis = new BufferedInputStream(new FileInputStream("C:\\Users\\ZDNF\\Desktop\\timg.jpg"));
            int i = bis.read(buf);
            while (i!=-1){
                ctx.writeAndFlush(buf);
                i=bis.read(buf);
                System.out.print(1);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            ctx.close();
        }
    }

    private void writeFinish(){
        ctx.close();
    }
}
