package com.thinkme.utils.lock.dislock;

import com.thinkme.utils.lock.exception.LockException;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁接口
 * Created by liangqq on 2017/2/27.
 */
public interface DistributedLock {

    /**
     * 加锁，在指定时间内获取锁失败会抛出
     *
     * @param waitTime 最长等待锁时间
     * @param timeUnit 时间单位
     * @throws LockException 加锁失败时抛出异常
     */
    void lock(long waitTime, TimeUnit timeUnit);

    /**
     * 加锁
     *
     * @param waitTime  最长等待锁时间
     * @param leaseTime 持有锁最长时间
     * @param timeUnit  时间单位
     * @throws LockException 加锁失败时抛出异常
     */
    void lock(long waitTime, long leaseTime, TimeUnit timeUnit);

    /**
     * 解锁
     */
    void unLock();

    /**
     * 判断锁对象是否被人持有
     */
    boolean isLock();

    /**
     * 判断是否被当前线程持有
     *
     * @return boolean
     */
    boolean isHeldByCurrentThread();
}
