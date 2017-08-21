package com.thinkme.utils.id;

import com.thinkme.utils.id.constant.VariableConst;
import org.junit.Before;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

public class SystemEnvWorkerIdTest extends AbstractWorkerIdTest {
    
    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();
    
    @Before
    public void setup() {
        environmentVariables.set(VariableConst.SELF_WORKER_ID_ENV, "13");
        super.setup();
    }
    
    @Override
    protected long getWorkerId() {
        return 13L;
    }
}
