package test.httpServer.annotation;

import cn.skyisbule.ioc.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skyisbule on 2018/2/15.
 */
@Controller
@Slf4j
public class ControllerTest {

    @Ioc
    ServiceTest test;

    @Url(value = "/index")
    @Json
    public void hello(){
        Map map = new HashMap();
        map.put("h","s");
        map.put("a","sd");
        //return map;
    }

}
