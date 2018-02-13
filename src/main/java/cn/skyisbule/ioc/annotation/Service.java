package cn.skyisbule.ioc.annotation;

import java.lang.annotation.*;

/**
 * Created by skyisbule on 2018/2/13.
 * bean管理标记
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {

}
