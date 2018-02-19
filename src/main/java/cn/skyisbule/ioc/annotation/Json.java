package cn.skyisbule.ioc.annotation;

import java.lang.annotation.*;

/**
 * Created by skyisbule on 2018/2/13.
 * rest接口标记
 */
@Target({ElementType.TYPE,ElementType.METHOD})

@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Json {
}
