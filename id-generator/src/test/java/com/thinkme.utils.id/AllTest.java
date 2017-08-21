package com.thinkme.utils.id;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApiWorkerIdTest.class,
        SystemPropertyWorkerIdTest.class,
        SystemEnvWorkerIdTest.class,
        CommonIdGeneratorTest.class,
        HostNameIdGeneratorTest.class,
        IPIdGeneratorTest.class
    })
public class AllTest {
}
