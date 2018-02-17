package cn.skyisbule.ioc.bean;

import cn.hutool.core.util.ReflectUtil;
import cn.skyisbule.ioc.annotation.Ioc;
import cn.skyisbule.ioc.annotation.Url;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by skyisbule on 2018/2/13.
 * 开放外部接口，供其他模块调用类和方法
 */
@Setter
@Getter
@Slf4j
public class BeanFactory {

    public static Map<String,Object> instanceMap = new HashMap<>();

    public static Map<String,Method> methodMap   = new HashMap<>();

    public static Map<String,Object> urlObj      = new HashMap<>();

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
                    ReqUrl = checkUrl(ReqUrl);
                    methodMap.put(ReqUrl,method);
                    log.info("成功提取到路由：{}",ReqUrl);
                    //将url->object添加进map 方便后期根据url取出对象
                    urlObj.put(ReqUrl,o);
                }
            }
        }
    }

    public static Object getObjByUrl(String url){
        return urlObj.get(url);
    }

    //对开发者写的url进行基本的判断和修改
    private String checkUrl(String url){
        //先判断是不是空
        if (url.length()==0){
            return "/";
        }
        //如果不是以'/'开头的，加上去
        if (!url.startsWith("/")){
            url = "/" + url;
        }
        //如果结尾写了'/' 去掉
        if (url.endsWith("/")&&url.length()>2){
            url = url.substring(0,url.length()-1);
        }
        return url;
    }


    //注册bean
    public void Di(){
        if (instanceMap.isEmpty())
            return;
        //遍历一下
        for (Map.Entry<String,Object> entry : instanceMap.entrySet()){
            Object o = entry.getValue();
            Field[] fields = ReflectUtil.getFields(o.getClass());
            for (Field field : fields){
                //如果被Ioc注解  意味着需要注入bean
                if (field.isAnnotationPresent(Ioc.class)){
                    String fieldName = field.getType().getName();
                    if (instanceMap.containsKey(fieldName)) {
                        log.info("成功注入bean：{}",fieldName);
                        ReflectUtil.setFieldValue(o, field, instanceMap.get(fieldName));
                    }else {
                        log.error("注入bean失败:{}",fieldName);
                    }
                }
            }
        }
    }

}
