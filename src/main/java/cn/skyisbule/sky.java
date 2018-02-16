package cn.skyisbule;

import cn.skyisbule.config.Environment;
import cn.skyisbule.ioc.Ioc;
import cn.skyisbule.mvc.router.Router;
import cn.skyisbule.server.netty.HttpServer;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by skyisbule on 2018/2/12.
 * 启动类
 */
@Slf4j
public class sky {

    //设置环境
    Environment environment = Environment.create();
    //ioc
    Ioc ioc                 = new Ioc();
    //路由控制器
    Router router           = new Router();


    public static sky creat(){
        return new sky();
    }

    public void run() throws Exception {
        //初始化ioc
        log.info("开始初始化ioc");
        ioc.initIoc();
        log.info("初始化ioc完成");
        //初始化路由;
        log.info("开始初始化路由");
        router.initRoute();
        log.info("初始化路由完成");

        log.info("开启服务器");
        new HttpServer().start(this);

    }

    public sky setPort(int port){
        environment.setPort(port);
        return this;
    }

    public sky setHost(String address){
        environment.setAddress(address);
        return this;
    }

    public Environment getEnvironment(){
        return environment;
    }

}
