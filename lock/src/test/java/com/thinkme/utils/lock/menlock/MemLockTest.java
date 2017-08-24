package com.thinkme.utils.lock.menlock;

import org.junit.Test;

import java.util.concurrent.locks.Lock;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by liangqq on 2017/3/7.
 */
public class MemLockTest {

    String key = "test1";
    final int LOOP_TIMES = 100000;

    @Test
    public void testSegmentLock() throws Exception {
        final Result result = new Result();
        Lock lock = new MemLockBuilder()
                .lockType(MemLockBuilder.MemLockType.SEGMENT)
                .lockKey(key)
                .build();
        testAdd(result, lock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullLock() throws Exception {
        Lock lock = new MemLockBuilder()
                .lockType(MemLockBuilder.MemLockType.SEGMENT)
                .lockKey("")
                .build();
    }

    @Test
    public void testWeakHashLock() throws Exception {
        final Result result = new Result();
        Lock lock = new MemLockBuilder()
                .lockType(MemLockBuilder.MemLockType.WEAK_HASH)
                .lockKey(key)
                .build();
        testAdd(result, lock);
    }

    private void testAdd(Result result, Lock lock) throws InterruptedException {
        for (int i = 0; i < LOOP_TIMES; i++) {
            lock.lock();
            result.inc();
            lock.unlock();
        }

        Thread thread = new Thread(() -> {
            for (int i = 0; i < LOOP_TIMES; i++) {
                lock.lock();
                result.inc();
                lock.unlock();
            }
        });
        thread.start();
        thread.join();

        assertThat(result.val()).isEqualTo(LOOP_TIMES * 2L);
    }

    class Result {
        long value = 0L;

        void inc() {
            value++;
        }

        long val(){
            return value;
        }
    }

}