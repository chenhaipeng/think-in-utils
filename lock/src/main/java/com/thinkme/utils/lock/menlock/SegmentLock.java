package com.thinkme.utils.lock.menlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 分段锁
 * Created by liangqq on 2017/3/7.
 */

class SegmentLock{

    private  int segments = 16; //默认分段数量

    private final Map<Integer, ReentrantLock> lockMap = new HashMap<>();

    public SegmentLock() {
        init(-1, false);
    }

    /**
     * @param counts 分段区域大小，默认值为16
     * @param fair   是否公平
     */
    public SegmentLock(int counts, boolean fair) {
        init(counts, fair);
    }

    /**
     *  获取锁对象
     * @param key 锁key
     * @return
     */
    public Lock getLock(String key){
        return lockMap.get(key.hashCode() % segments);
    }

    private void init(int counts, boolean fair) {
        if (counts > 0 && counts <= 256) {
            segments = counts;
        }
        for (int i = 0; i < segments; i++) {
            lockMap.put(i, new ReentrantLock(fair));
        }
    }

}
