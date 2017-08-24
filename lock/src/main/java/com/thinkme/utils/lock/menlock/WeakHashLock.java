package com.thinkme.utils.lock.menlock;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 弱引用哈希锁，依赖gc回收锁对象
 *
 * Created by liangqq on 2017/3/7.
 */
class WeakHashLock {

    private static ConcurrentHashMap<String, WeakReference<ReentrantLock>> lockMap = new ConcurrentHashMap<>();

    private WeakHashLock() {
    }

    /**
     * 获取key对应的锁对象
     * @param key 锁key
     * @return 锁对象
     */
    public static Lock getLock(String key) {
        WeakReference<ReentrantLock> lockRef = lockMap.get(key);
        Lock lock = (lockRef == null ? null : lockRef.get());
        ReentrantLock reentrantLock = new ReentrantLock();
        while (lock == null) {
            lockMap.putIfAbsent(key, new WeakReference<>(reentrantLock));
            lockRef = lockMap.get(key);
            lock = (lockRef == null ? null : lockRef.get());
            if (lock != null) {
                return lock;
            }
            // 假如lock还是空的话清楚掉
            clearEmptyRef(key);
        }
        return lock;
    }

    private static void clearEmptyRef(String key) {
        lockMap.remove(key);
    }
}
