package com.thinkme.utils.misc;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

public class SamplerTest {

	@Test
	public void test() {
		com.thinkme.utils.concurrent.throttle.Sampler sampler = com.thinkme.utils.concurrent.throttle.Sampler.create(10.5);
		int hits = 0;
		for (int i = 0; i < 10000; i++) {
			if (sampler.select()) {
				hits++;
			}
		}
		System.out.println("sample 10.5% in 10000 hits should close to 1050, actual is " + hits);

		assertThat(hits).isBetween(900, 1200);
		//////////
		com.thinkme.utils.concurrent.throttle.Sampler sampler2 = com.thinkme.utils.concurrent.throttle.Sampler.create(0.5);

		hits = 0;
		for (int i = 0; i < 10000; i++) {
			if (sampler2.select()) {
				hits++;
			}
		}
		System.out.println("sample 0.5% in 10000 hits should close to 50, actual is " + hits);
		assertThat(hits).isBetween(20, 100);

	}

	@Test
	public void always() {
		com.thinkme.utils.concurrent.throttle.Sampler sampler = com.thinkme.utils.concurrent.throttle.Sampler.create(0d);
		assertThat(sampler).isInstanceOf(com.thinkme.utils.concurrent.throttle.Sampler.NeverSampler.class);
		sampler = com.thinkme.utils.concurrent.throttle.Sampler.create(100d);
		assertThat(sampler).isInstanceOf(com.thinkme.utils.concurrent.throttle.Sampler.AlwaysSampler.class);

		try {
			sampler = com.thinkme.utils.concurrent.throttle.Sampler.create(101d);
			fail("shoud fail before");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(IllegalArgumentException.class);
		}

		try {
			sampler = com.thinkme.utils.concurrent.throttle.Sampler.create(-2.2);
			fail("shoud fail before");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(IllegalArgumentException.class);
		}
	}
}
