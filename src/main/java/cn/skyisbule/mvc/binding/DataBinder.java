package cn.skyisbule.mvc.binding;

import cn.hutool.core.util.ReflectUtil;
import cn.skyisbule.ioc.bean.BeanFactory;
import cn.skyisbule.mvc.http.SkyRequest;
import lombok.extern.slf4j.Slf4j;
import sun.management.MethodInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

/**
 * Created by skyisbule on 2018/2/17.
 * 用于绑定请求参数
 */
@Slf4j
public class DataBinder {

    private SkyRequest request;
    private Object object;

    public Object[] getResult(SkyRequest request, Object obj, Method method) throws
            ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.request = request;
        this.object  = obj;
        //处理所有参数
        Type[] parms = method.getGenericParameterTypes();
        Object[] args = new Object[parms.length];
        int i = 0;
        for (Type type:parms){
            //type.getClass().isAnnotationPresent()
            //log.info(type.getTypeName());
            switch (type.getTypeName()){
                case "java.lang.String":
                    args[i] = "test";
                    break;
                case "int":
                    args[i] = 1;
                    break;
                default:
                    Object o = binding(type.getTypeName());
                    args[i]  = o;
            }
            i++;
        }
        return args;
    }

    private Object binding(String pkgName){
        //需要return的对象
        Object o = null;
        try {
            o = Class.forName(pkgName).newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        Field[] fields = ReflectUtil.getFields(o.getClass());
        for (Field field : fields){
            String name = field.getName();
            ReflectUtil.setFieldValue(o,field,listToString(request.getParameters().get(name)));
        }
        return o;
    }

    private String listToString(List<String> list){
        StringBuilder b = new StringBuilder();
        for (String a : list){
            b.append(a);
        }
        return b.toString();
    }
}
