package test.httpServer.annotation;

import cn.skyisbule.ioc.annotation.Controller;
import cn.skyisbule.ioc.annotation.Ioc;
import cn.skyisbule.ioc.annotation.Service;
import cn.skyisbule.ioc.annotation.Url;

/**
 * Created by skyisbule on 2018/2/15.
 */
@Controller
public class ControllerTest {

    @Ioc
    ServiceTest test;

    @Url(value = "/index")
    public String hello(){
        return test.get();
    }

}
