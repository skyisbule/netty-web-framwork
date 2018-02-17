package test.httpServer.annotation;

import cn.skyisbule.ioc.annotation.Controller;
import cn.skyisbule.ioc.annotation.Ioc;
import cn.skyisbule.ioc.annotation.Service;
import cn.skyisbule.ioc.annotation.Url;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by skyisbule on 2018/2/15.
 */
@Controller
@Slf4j
public class ControllerTest {

    @Ioc
    ServiceTest test;

    @Url(value = "/index")
    public String hello(String a,int b,user user){
        log.debug(a);
        log.debug(String.valueOf(b));
        log.debug(user.getName());

        return test.get();
    }

}
