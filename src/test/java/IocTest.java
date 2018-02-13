import cn.skyisbule.ioc.loader.Loader;

import java.util.Arrays;

/**
 * Created by skyisbule on 2018/2/13.
 */
public class IocTest {

    public static void main(String[] a){
        try {
            new Loader().init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
