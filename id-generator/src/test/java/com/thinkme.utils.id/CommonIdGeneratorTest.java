package com.thinkme.utils.id;

import com.thinkme.utils.id.fixture.FixClock;
import com.thinkme.utils.id.time.AbstractClock;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CommonIdGeneratorTest {
    
    @Before
    public void init() {
        CommonIdGenerator.setClock(AbstractClock.systemClock());
    }
    
    @Test
    public void generateId() throws Exception {
        int threadNumber = Runtime.getRuntime().availableProcessors() << 1;
        ExecutorService executor = Executors.newFixedThreadPool(threadNumber);
    
        final int taskNumber = threadNumber << 2;
        final CommonIdGenerator idGenerator = new CommonIdGenerator();
        Set<Long> hashSet = new HashSet<>();
        for (int i = 0; i < taskNumber; i++) {
            hashSet.add(executor.submit(new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    return (Long) idGenerator.generateId();
                }
            }).get());
        }
        System.out.println(hashSet);
        assertThat(hashSet.size(), is(taskNumber));
       for (Long l : hashSet){
           System.out.println((l+"").length());
           break;
       }
    }
    
    @Test
    public void testMaxSequence() throws Exception {
        assertThat(maxId((1 << 12) - 1), is((1L << 12L) - 2));
        assertThat(maxId(1 << 12), is((1L << 12L) - 1));
        assertThat(maxId((1 << 12) + 1), is((1L << 12L) - 1));
        assertThat(maxId(1 << 13), is((1L << 12L) - 1));
        assertThat(maxId((1 << 13) + 1), is((1L << 12L) - 1));
    }
    
    private long maxId(final int maxSequence) {
        CommonIdGenerator idGenerator = new CommonIdGenerator();
        CommonIdGenerator.setClock(new FixClock(maxSequence));
        long id = 0;
        long preId = 0;
        while (id < (1L << 12)) {
            preId = id;
            id = (Long) idGenerator.generateId();
        }
        return preId;
    }
}
