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
    private String                content     = null;

    public void addHeader(String s,Object o){
        headers.add(s,o);
    }

    public void addCookie(){

    }

    public void write(){
        content = "<html><body><h1>haha</h1></body></html>";

        FullHttpResponse httpResponse = this.build();

        ctx.writeAndFlush(httpResponse);

        writeFinish(ctx);

    }





    /**
     *  创建一个自己，构造一个基于（netty的DefaultFullHttpResponse）的Response对象
     */
    public static SkyResponse buildSelf(ChannelHandlerContext ctx){
        SkyResponse response = new SkyResponse();
        response.ctx=ctx;
        return response;
    }

    /**
     * 通过自身数据构造一个需要写回用户的Response对象，
     */
    private FullHttpResponse build(){
        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, OK, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8));


        return httpResponse;
    }

    /**
     * 写完毕 断开连接
     */
    public void writeFinish(ChannelHandlerContext ctx){
        ctx.close();
    }

}
