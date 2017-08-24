package com.thinkme.utils.lock.dislock;


import com.thinkme.utils.lock.exception.LockException;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by liangqq on 2017/2/23.
 */
@Slf4j
public class DistributedLockTest {

    private static final String KEY = "key$$$$";

    private static DistributedLockFactory distributedLockFactory;

    @BeforeClass
    public static void init() throws IOException {
        String path = Thread.currentThread().getContextClassLoader().getResource("redissionConf.yml").getPath();
        Config config = Config.fromYAML(new File(path));
        distributedLockFactory = new DistributedLockFactory(config);
    }

    /**
     * 测试场景：线程1获取锁后，线程2等待时间下获取锁失败
     *
     * @throws Exception
     */
    @Test
    public void lockMutex() throws Exception {
        DistributedLock lock = distributedLockFactory.getLock(KEY);
        int waitTime = 1000;
        lock.lock(waitTime, TimeUnit.MILLISECONDS);

        Thread thread = new Thread(() -> {
            DistributedLock lock2 = distributedLockFactory.getLock(KEY);
            try{
                lock2.lock(waitTime, TimeUnit.MICROSECONDS);
            }catch(LockException e){
                assertThat(lock2.isHeldByCurrentThread()).isFalse();
            }finally {
                lock2.unLock();
            }
        });

        thread.start();
        lock.unLock();

        thread.join();
    }

    /**
     * 测试场景：线程1获取锁后，线程2等待时间内获取锁成功
     *
     * @throws Exception
     */
    @Test
    public void lockLockWaitTime() throws Exception {
        DistributedLock lock = distributedLockFactory.getLock(KEY);
        int waitTime = 10;
        try {
            lock.lock(waitTime, TimeUnit.MICROSECONDS);
        } finally {
            lock.unLock();
        }

        Thread thread = new Thread(() -> {
            DistributedLock lock2 = distributedLockFactory.getLock(KEY);
            try {
                lock2.lock(waitTime, TimeUnit.MICROSECONDS);
                assertThat(lock2.isHeldByCurrentThread()).isTrue();
            } finally {
                lock.unLock();
            }
        });

        thread.start();
        thread.join();
    }

    @Test
    public void lockLeaseTime() throws Exception {
        DistributedLock lock = distributedLockFactory.getLock(KEY);
        int waitTime = 10;
        int leaseTime = 50;
        try {
            lock.lock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
            TimeUnit.MILLISECONDS.sleep(leaseTime);
            assertThat(lock.isLock()).isFalse();
        } finally {
            lock.unLock();
        }
    }

    @Test
    public void isLock() throws Exception {
        DistributedLock lock = distributedLockFactory.getLock(KEY);
        lock.lock(10, TimeUnit.MICROSECONDS);
        try {
            lock.lock(10, TimeUnit.MICROSECONDS);
            assertThat(lock.isLock()).isTrue();
        } finally {
            lock.unLock();
        }
    }

}