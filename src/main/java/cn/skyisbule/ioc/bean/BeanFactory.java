package cn.skyisbule.ioc.bean;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by skyisbule on 2018/2/13.
 */
@Setter
@Getter
public class BeanFactory {

    public static Map<String,Object> instanceMap = new HashMap<>();

    public static Map<String,Method> methodMap = new HashMap<>();

}
