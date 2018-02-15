package test.httpServer.annotation;

import cn.skyisbule.ioc.annotation.Service;
import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by skyisbule on 2018/2/15.
 */
@Service
public class ServiceTest {

    public String get(){
        return "hello ioc";
    }
}
