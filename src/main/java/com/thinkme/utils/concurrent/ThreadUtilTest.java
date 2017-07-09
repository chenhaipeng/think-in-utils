package com.thinkme.utils.concurrent;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import com.thinkme.utils.base.ObjectUtil;

public class ThreadUtilTest {
	@Test
	public void testCaller(){
		hello();
		new MyClass().hello();
		assertThat(ThreadUtil.getCurrentClass()).isEqualTo("ThreadUtilTest");
		assertThat(ThreadUtil.getCurrentMethod()).isEqualTo("ThreadUtilTest.testCaller()");
		
	}
	
	private void hello(){
		assertThat(ThreadUtil.getCallerClass()).isEqualTo("ThreadUtilTest");
		assertThat(ThreadUtil.getCallerMethod()).isEqualTo("ThreadUtilTest.testCaller()");
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		System.out.println(ObjectUtil.toPrettyString(stacktrace));
	}

	public static class MyClass{
		public void hello(){
			assertThat(ThreadUtil.getCallerClass()).isEqualTo("ThreadUtilTest");
			assertThat(ThreadUtil.getCallerMethod()).isEqualTo("ThreadUtilTest.testCaller()");
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			System.out.println(ObjectUtil.toPrettyString(stacktrace));
		}
	}
}
