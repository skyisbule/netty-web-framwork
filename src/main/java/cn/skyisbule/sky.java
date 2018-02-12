package cn.skyisbule;

import cn.skyisbule.config.Environment;
import cn.skyisbule.server.netty.HttpServer;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by skyisbule on 2018/2/12.
 * 启动类
 */
@Slf4j
public class sky {

    Environment environment = Environment.create();

    public static sky creat(){
        return new sky();
    }

    public void run() throws Exception {

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
