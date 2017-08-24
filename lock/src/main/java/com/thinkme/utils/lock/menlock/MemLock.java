package com.thinkme.utils.lock.menlock;

/**
 * Created by liangqq on 2017/3/7.
 */
public interface MemLock{

    /**
     * 加锁
     * @param key key
     */
    void lock(String key);

    /**
     * 解锁
     * @param key key
     */
    void unlock(String key);
}
