package com.thinkme.utils.id;

import com.thinkme.utils.id.fixture.FixClock;
import com.thinkme.utils.id.time.AbstractClock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public abstract class AbstractWorkerIdTest {
    
    protected abstract long getWorkerId();
    
    @Before
    public void setup() {
        CommonIdGenerator.setClock(new FixClock(1));
        CommonIdGenerator.initWorkerId();
    }
    
    @After
    public void clear() {
        CommonIdGenerator.setClock(AbstractClock.systemClock());
        CommonIdGenerator.setWorkerId(0L);
    }
    
    @Test
    public void testWorkerId() {
        CommonIdGenerator idGenerator = new CommonIdGenerator();
        
        assertThat((Long) idGenerator.generateId(), is(getWorkerId() << 12L));
        //assertThat(idGenerator.getLastTime(), is(CommonIdGenerator.EPOCH));
        assertThat(idGenerator.getSequence(), is(0L));
        assertThat(CommonIdGenerator.getWorkerId(), is(getWorkerId()));
    }
}
