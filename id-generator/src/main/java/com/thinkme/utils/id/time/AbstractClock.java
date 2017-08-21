package com.thinkme.utils.id.time;

/**
 * 抽象的时钟类
 *
 * <p>抽取此类主要为了解决问题时间相关的测试问题</p>
 */
public abstract class AbstractClock {

    /**
     * 创建系统时钟.
     *
     * @return 系统时钟
     */
    public static AbstractClock systemClock() {
        return new SystemClock();
    }

    /**
     * 返回从纪元开始的毫秒数.
     *
     * @return 从纪元开始的毫秒数
     */
    public abstract long millis();

    private static final class SystemClock extends AbstractClock {

        @Override
        public long millis() {
            return System.currentTimeMillis();
        }
    }
}
