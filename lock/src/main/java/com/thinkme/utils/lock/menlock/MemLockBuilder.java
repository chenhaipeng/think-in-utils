package com.thinkme.utils.lock.menlock;

import lombok.NoArgsConstructor;

import java.util.concurrent.locks.Lock;

/**
 * Created by liangqq on 2017/3/7.
 */
@NoArgsConstructor
public final class MemLockBuilder {

    private MemLockType lockType;
    private String lockKey;

    public MemLockBuilder lockType(MemLockType lockType) {
        this.lockType = lockType;
        return this;
    }

    public MemLockBuilder lockKey(String lockKey) {
        // 为了保持jar的依赖简单，不引入guava
        if(null == lockKey || lockKey.isEmpty()){
            throw new IllegalArgumentException("lockKey cannot be empty.");
        }

        this.lockKey = lockKey;
        return this;
    }

    public Lock build() {
        Lock lock = null;
        switch (lockType) {
            case SEGMENT:
                lock = new SegmentLock().getLock(lockKey);
                break;
            case WEAK_HASH:
                lock = WeakHashLock.getLock(lockKey);
                break;
        }
        return lock;
    }

    public enum MemLockType {
        /**
         * 分段锁
         */
        SEGMENT,
        /**
         * 弱哈希锁
         */
        WEAK_HASH
    }
}
