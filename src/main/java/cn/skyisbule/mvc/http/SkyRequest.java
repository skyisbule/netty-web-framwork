package cn.skyisbule.mvc.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.multipart.*;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by skyisbule on 2018/2/12.
 * Http请求的封装类
 */
@Slf4j
@Getter
public class SkyRequest {

    private static final HttpDataFactory HTTP_DATA_FACTORY = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);

    private String url;
    private String uri;
    private String method;
    private boolean keepAlive;
    private ByteBuf body = Unpooled.copiedBuffer("", CharsetUtil.UTF_8);

    private Map<String,String>        headers     =  null;
    private Map<String,Object>        attributes  =  null;
    private Map<String,List<String>>  parameters  =  new HashMap<>();
    private Map<String,String>        pathParams  =  null;
    private Map<String,String>        cookies     =  new HashMap<>();

    private void init(FullHttpRequest request){
        //请求路径
        this.uri = request.uri();
        this.method = request.method().name();
        //处理请求头
        HttpHeaders httpHeaders = request.headers();
        if (httpHeaders.size()>0){
            headers = new HashMap<>();
            httpHeaders.forEach(head->headers.put(head.getKey(),head.getValue()));
        }else {
            log.warn("未检测到请求头 请求路径：{}",uri);
        }
        //处理请求体
        this.body = request.content().copy();
        //解析GET请求参数
        Map<String, List<String>> parameters = new QueryStringDecoder(request.uri(), CharsetUtil.UTF_8).parameters();
        if (null != parameters) {
            this.parameters = new HashMap<>();
            this.parameters.putAll(parameters);
        }
        //解析post请求的表单数据
        if (method.equals("POST")){
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(HTTP_DATA_FACTORY, request);
            decoder.getBodyHttpDatas().forEach(this::parseData);
        }
        //解析cookie
        String cookie = headers.get("Cookie");
        if (cookie!=null){
            System.out.println(cookie);
            String[] reqCookies = cookie.split("&");
            String[] cookieData;
            for (String cookieTemp:reqCookies){
                cookieData = cookieTemp.split(":");
                cookies.put(cookieData[0],cookieData[1]);
            }
        }
        //处理完成
    }

    private void parseData(InterfaceHttpData data) {
        try {
            switch (data.getHttpDataType()) {
                //普通POST请求
                case Attribute:
                    Attribute attribute = (Attribute) data;
                    String name = attribute.getName();
                    String value = attribute.getValue();
                    this.parameters.put(name, Collections.singletonList(value));
                    break;
                //带文件上传的请求
                case FileUpload:
                    FileUpload fileUpload = (FileUpload) data;
                    parseFileUpload(fileUpload);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            log.error("post参数解析错误 url:{}：{}",uri,e);
        } finally {
            data.release();
        }
    }

    private void parseFileUpload(FileUpload fileUpload) throws IOException {
        //todo 后期再做文件上传的处理
    }

    public static SkyRequest build(FullHttpRequest fullHttpRequest){
        SkyRequest request = new SkyRequest();
        request.init(fullHttpRequest);
        return request;
    }

}
