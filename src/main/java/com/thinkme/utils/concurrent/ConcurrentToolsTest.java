package com.thinkme.utils.concurrent;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import com.thinkme.utils.concurrent.jsr166e.LongAdder;

public class ConcurrentToolsTest {

	@Test
	public void longAdder() {
		LongAdder counter = ConcurrentTools.longAdder();
		counter.increment();
		counter.add(2);
		assertThat(counter.longValue()).isEqualTo(3L);
	}

}
