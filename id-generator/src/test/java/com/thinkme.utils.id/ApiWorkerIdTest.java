package com.thinkme.utils.id;

import org.junit.Before;

public class ApiWorkerIdTest extends AbstractWorkerIdTest {
    
    @Before
    public void init() {
        CommonIdGenerator.setWorkerId(11L);
    }
    
    @Override
    protected long getWorkerId() {
        return 11;
    }
}
