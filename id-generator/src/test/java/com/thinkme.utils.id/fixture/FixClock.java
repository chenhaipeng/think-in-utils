package com.thinkme.utils.id.fixture;

import com.thinkme.utils.id.CommonIdGenerator;
import com.thinkme.utils.id.time.AbstractClock;

import java.util.concurrent.atomic.AtomicInteger;

public class FixClock extends AbstractClock {
    
    private final int expectedInvokedTimes = 0;
    
    private final AtomicInteger invokedTimes = new AtomicInteger();
    
    private long current = CommonIdGenerator.EPOCH;

    public FixClock(long current) {
        this.current = current;
    }

    @Override
    public long millis() {
        if (invokedTimes.getAndIncrement() < expectedInvokedTimes) {
            return current;
        }
        invokedTimes.set(0);
        return ++current;
    }
}