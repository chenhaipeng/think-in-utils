package com.thinkme.utils.id;

import com.thinkme.utils.id.constant.VariableConst;
import com.thinkme.utils.id.time.AbstractClock;

import java.util.Calendar;


/**
 * 自生成Id生成器.
 * <p>
 * <p>
 * 长度为64bit,从高位到低位依次为
 * </p>
 * <p>
 * <pre>
 * 1bit   符号位
 * 41bits 时间偏移量从2017年3月1日零点到现在的毫秒数
 * 10bits 工作进程Id
 * 12bits 同一个毫秒内的自增量
 * </pre>
 * <p>
 * <p>
 * 工作进程Id获取优先级: 系统变量{@code self.id.generator.worker.id} 大于 环境变量{@code SELF_ID_GENERATOR_WORKER_ID}
 * ,另外可以调用@{@code CommonSelfIdGenerator.setWorkerId}进行设置
 * </p>
 *
 * @author gaohongtao
 */
public class CommonIdGenerator implements IdGenerator {

    // 新纪元时间
    public static final long EPOCH;

    // 自增序列位数
    private static final long SEQUENCE_BITS = 12L;

    // 机器位
    private static final long WORKER_ID_BITS = 10L;

    private static final long SEQUENCE_MASK = (1L << SEQUENCE_BITS) - 1L;

    private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;

    //时间戳向左移动的最大位数
    private static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;

    // 机器Id最大值
    private static final long WORKER_ID_MAX_VALUE = 1L << WORKER_ID_BITS;

    // 机器Id
    private static long workerId;

    public static long getWorkerId() {
        return workerId;
    }

    public static void setWorkerId(long workerId) {
        CommonIdGenerator.workerId = workerId;
    }

    private static AbstractClock clock = AbstractClock.systemClock();

    public static AbstractClock getClock() {
        return clock;
    }

    public static void setClock(AbstractClock clock) {
        CommonIdGenerator.clock = clock;
    }

    private long sequence;

    private long lastTime;

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    static {
        // 从2017年1月1日开始
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.MARCH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        EPOCH = calendar.getTimeInMillis();
        initWorkerId();
    }

    public CommonIdGenerator(){

    }

    /**
     * 设置工作进程Id.
     *
     * @param workerId 工作进程Id
     */
    public static void setWorkerId(final Long workerId) {
        Validate.checkArgument(workerId >= 0L && workerId < WORKER_ID_MAX_VALUE);
        CommonIdGenerator.workerId = workerId;
    }

    /**
     * 获取工作Id的二进制长度.
     *
     * @return 工作Id的二进制长度
     */
    public static long getWorkerIdLength() {
        return WORKER_ID_BITS;
    }

    /**
     * 生成Id.
     *
     * @return 返回@{@link Long}类型的Id
     */
    @Override
    public synchronized long generateId() {
        long time = clock.millis();
        Validate.checkState(lastTime <= time, "Clock is moving backwards, last time is %d milliseconds, current time is %d milliseconds", lastTime, time);
        if (lastTime == time) {
            // 单位时间分配值超出了
            if (0L == (++sequence & SEQUENCE_MASK)) {
                //相同毫秒内，序列号自增
                time = waitUntilNextTime(time);
            }
        } else {
            sequence = 0;
        }
        lastTime = time;
//        log.debug("{}-{}-{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(lastTime)), workerId, sequence);
        return ((time - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (workerId << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
    }

    /**
     * //同一毫秒的序列数已经达到最大
     * @param lastTime
     * @return
     */
    private long waitUntilNextTime(final long lastTime) {
        long time = clock.millis();
        while (time <= lastTime) {
            time = clock.millis();
        }
        return time;
    }

    static void initWorkerId() {
        String workerId = System.getProperty(VariableConst.SELF_WORKER_ID_PROPERTY);
        if (!Validate.isNullOrEmpty(workerId)) {
            setWorkerId(Long.valueOf(workerId));
            return;
        }
        workerId = System.getenv(VariableConst.SELF_WORKER_ID_ENV);
        if (Validate.isNullOrEmpty(workerId)) {
            return;
        }
        setWorkerId(Long.valueOf(workerId));
    }

    private static class Validate{

        private Validate(){}

        public static void checkArgument(boolean expression){
            if(!expression){
                throw new IllegalArgumentException();
            }
        }

        public static void checkState(boolean expression, String errorMessageTemplate, Object... vars){
            if (!expression) {
                throw new IllegalStateException(String.format(errorMessageTemplate, vars));
            }
        }

        public static boolean isNullOrEmpty(String string){
            return string == null || string.isEmpty();
        }
    }
}
