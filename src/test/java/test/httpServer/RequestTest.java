package test.httpServer;

import cn.skyisbule.sky;

/**
 * Created by skyisbule on 2018/2/12.
 * 测试时间：2018-2-12 20:50:30
 * 初步完成请求处理 测试请求效果 访问 127.0.0.1 得到
 *
 21:23:56.520 [nioEventLoopGroup-3-1] INFO cn.skyisbule.mvc.http.SkyRequest - uri /
 21:23:56.520 [nioEventLoopGroup-3-1] INFO cn.skyisbule.mvc.http.SkyRequest - method GET
 21:23:56.520 [nioEventLoopGroup-3-1] INFO cn.skyisbule.mvc.http.SkyRequest - headers {content-length=0, Cache-Control=max-age=0,。。。此处省略}
 21:23:56.520 [nioEventLoopGroup-3-1] INFO cn.skyisbule.mvc.http.SkyRequest - body PooledUnsafeDirectByteBuf(ridx: 0, widx: 0, cap: 0)
 */
public class RequestTest {

    public static void main(String[] a ) throws Exception {
        sky.creat()
                .setPort(9090)
                .run();
    }
}
