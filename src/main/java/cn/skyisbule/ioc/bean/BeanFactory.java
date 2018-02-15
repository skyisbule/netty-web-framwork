package cn.skyisbule.ioc.bean;

import cn.hutool.core.util.ReflectUtil;
import cn.skyisbule.ioc.annotation.Url;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by skyisbule on 2018/2/13.
 * 开放外部接口，供其他模块调用类和方法
 */
@Setter
@Getter
public class BeanFactory {

    public static Map<String,Object> instanceMap = new HashMap<>();

    public static Map<String,Method> methodMap   = new HashMap<>();

    public void addInstance(String className,Object o){
        instanceMap.put(className,o);
    }

    public void collectMethod(){
        if (instanceMap.isEmpty())
            return;
        //遍历一遍，提取method注入对象
        for (Map.Entry<String, Object> entry : instanceMap.entrySet()) {
            //取出一个对象
            Object o = entry.getValue();
            //拿到所有的方法
            Method[] methods = ReflectUtil.getMethods(o.getClass());
            for (Method method : methods){
                //如果被Url注解
                if (method.isAnnotationPresent(Url.class)){
                    //拿到注解的URL
                    String ReqUrl = method.getAnnotation(Url.class).value();
                    //todo  这里要对url进行一个基本的判断和处理
                    methodMap.put(ReqUrl,method);
                }
            }
        }
    }

}
