package cn.skyisbule;

import cn.skyisbule.config.Environment;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by skyisbule on 2018/2/12.
 * 启动类
 */
@Slf4j
public class sky {

    Environment environment = Environment.create();

    public static sky creat(){
        return new sky();
    }


}
