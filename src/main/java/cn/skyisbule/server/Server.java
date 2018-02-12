package cn.skyisbule.server;

import cn.skyisbule.sky;

/**
 * Created by skyisbule on 2018/2/12.
 * http服务器启动类
 */
public interface Server {

    public void start(sky sky) throws Exception;

    public void stop();

}
