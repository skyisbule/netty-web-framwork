package cn.skyisbule.ioc;

import cn.skyisbule.ioc.loader.Loader;

/**
 * Created by skyisbule on 2018/2/13.
 * 服务注入标记
 */
public class Ioc {

    Loader reader = new Loader();

    public void initIoc() throws Exception {
        reader.init();
    }

}
