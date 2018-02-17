package cn.skyisbule.mvc.router;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * Created by skyisbule on 2018/2/17.
 * 返回映射实体
 */
@Getter
@Setter
public class RouteHandle {

    public String url;
    public Method method;
    public String ReqMethod;
    public boolean is404 = true;

}
