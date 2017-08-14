package com.thinkme.utils.concurrent;

import com.thinkme.utils.base.ObjectUtil;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadUtilTest {
	@Test
	public void testCaller(){
		hello();
		new MyClass().hello();
		assertThat(ThreadUtil.getCurrentClass()).isEqualTo("com.thinkme.utils.concurrent.ThreadUtilTest");
		assertThat(ThreadUtil.getCurrentMethod()).isEqualTo("com.thinkme.utils.concurrent.ThreadUtilTest.testCaller()");
		
	}
	
	private void hello(){
		//会带上包名的
		assertThat(ThreadUtil.getCallerClass()).isEqualTo("com.thinkme.utils.concurrent.ThreadUtilTest");
		assertThat(ThreadUtil.getCallerMethod()).isEqualTo("com.thinkme.utils.concurrent.ThreadUtilTest.testCaller()");
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		System.out.println(ObjectUtil.toPrettyString(stacktrace));
	}

	public static class MyClass{
		public void hello(){
			assertThat(ThreadUtil.getCallerClass()).isEqualTo("com.thinkme.utils.concurrent.ThreadUtilTest");
			assertThat(ThreadUtil.getCallerMethod()).isEqualTo("com.thinkme.utils.concurrent.ThreadUtilTest.testCaller()");
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			System.out.println(ObjectUtil.toPrettyString(stacktrace));
		}
	}
}
