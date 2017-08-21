package com.thinkme.utils.id;

import com.thinkme.utils.id.constant.VariableConst;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.ProvideSystemProperty;

public class SystemPropertyWorkerIdTest extends AbstractWorkerIdTest {

    @Rule
    public final ProvideSystemProperty provideSystemProperty = new ProvideSystemProperty(VariableConst.SELF_WORKER_ID_PROPERTY, "12");

    @Override
    protected long getWorkerId() {
        return 12L;
    }
}
