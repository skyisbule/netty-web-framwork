package cn.skyisbule.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by skyisbule on 2018/2/12.
 */
public class SkyThreadFactory implements ThreadFactory {
    private final String prefix;
    private final LongAdder threadNumber = new LongAdder();

    public SkyThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        threadNumber.add(1);
        return new Thread(runnable, prefix + " thread-" + threadNumber.intValue());
    }
}
