package cn.skyisbule.mvc.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * Created by skyisbule on 2018/2/12.
 * httpResponse 包装类
 */
@Getter
@Setter
public class SkyResponse {
    private HttpHeaders headers     = new DefaultHttpHeaders(false);
    private Set<Cookie> cookies     = new HashSet<>(4);
    private int                   statusCode  = 200;
    private boolean               isCommit    = false;
    private ChannelHandlerContext ctx         = null;
    private CharSequence          contentType = null;
    private CharSequence          dateString  = null;

    public void addCookie(){

    }

    public void write(){
        //响应头
        //response.headers().set(getHeaders());
        String content = "<html><body><h1>haha</h1></body></html>";
        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, OK, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8));

        ctx.writeAndFlush(httpResponse);
        ctx.close();
    }

    public static SkyResponse build(ChannelHandlerContext ctx){
        SkyResponse response = new SkyResponse();
        response.ctx=ctx;
        return response;
    }

    public void writeFinish(){

    }
}
