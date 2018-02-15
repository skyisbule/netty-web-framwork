package cn.skyisbule.ioc.annotation;

import java.lang.annotation.*;

/**
 * Created by skyisbule on 2018/2/13.
 * 依赖注入标记
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ioc {

}
