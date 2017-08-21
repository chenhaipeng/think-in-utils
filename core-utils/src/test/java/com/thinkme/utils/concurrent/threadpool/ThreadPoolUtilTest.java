/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.thinkme.utils.concurrent.threadpool;

import com.thinkme.utils.concurrent.ThreadUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.test.log.LogbackListAppender;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadPoolUtilTest {
	@Test
	public void buildThreadFactory() {

		Runnable testRunnable = new Runnable() {
			@Override
			public void run() {
			}
		};
		// 测试name格式
		ThreadFactory threadFactory = ThreadPoolUtil.buildThreadFactory("example");
		Thread thread = threadFactory.newThread(testRunnable);

		assertThat(thread.getName()).isEqualTo("example-0");
		assertThat(thread.isDaemon()).isFalse();

		// 测试daemon属性设置
		threadFactory = ThreadPoolUtil.buildThreadFactory("example", true);
		Thread thread2 = threadFactory.newThread(testRunnable);

		assertThat(thread.getName()).isEqualTo("example-0");
		assertThat(thread2.isDaemon()).isTrue();
	}

	@Test
	public void gracefulShutdown() throws InterruptedException {

		Logger logger = LoggerFactory.getLogger("test");
		LogbackListAppender appender = new LogbackListAppender();
		appender.addToLogger("test");

		// time enough to shutdown
		ExecutorService pool = Executors.newSingleThreadExecutor();
		Runnable task = new Task(logger, 200, 0);
		pool.execute(task);
		ThreadPoolUtil.gracefulShutdown(pool, 1000, TimeUnit.MILLISECONDS);
		assertThat(pool.isTerminated()).isTrue();
		assertThat(appender.getFirstLog()).isNull();

		// time not enough to shutdown,call shutdownNow
		appender.clearLogs();
		pool = Executors.newSingleThreadExecutor();
		task = new Task(logger, 1000, 0);
		pool.execute(task);
		ThreadPoolUtil.gracefulShutdown(pool, 500, TimeUnit.MILLISECONDS);
		assertThat(pool.isTerminated()).isTrue();
		assertThat(appender.getFirstLog().getMessage()).isEqualTo("InterruptedException");

		// self thread interrupt while calling gracefulShutdown
		appender.clearLogs();

		final ExecutorService self = Executors.newSingleThreadExecutor();
		task = new Task(logger, 100000, 0);
		self.execute(task);

		final CountDownLatch lock = new CountDownLatch(1);
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				lock.countDown();
				ThreadPoolUtil.gracefulShutdown(self, 200000, TimeUnit.MILLISECONDS);
			}
		});
		thread.start();
		lock.await();
		thread.interrupt();
		ThreadUtil.sleep(500);
		assertThat(appender.getFirstLog().getMessage()).isEqualTo("InterruptedException");

		ThreadPoolUtil.gracefulShutdown(null, 1000);
		ThreadPoolUtil.gracefulShutdown(null, 1000, TimeUnit.MILLISECONDS);
	}

	/**
	 * 用户没有捕捉异常导致中断了线程池中的线程, 使得SchedulerService无法继续执行.
	 */
	@Test
	public void wrapException() {
		ScheduledThreadPoolExecutor executor = ThreadPoolBuilder.scheduledPool().build();
		ExceptionTask task = new ExceptionTask();
		executor.scheduleAtFixedRate(task, 0, 100, TimeUnit.MILLISECONDS);

		ThreadUtil.sleep(500);

		// 线程第一次跑就被中断
		assertThat(task.counter.get()).isEqualTo(1);
		System.out.println("-------actual run:" + task.counter.get());
		ThreadPoolUtil.gracefulShutdown(executor, 1000);

		////////
		executor = ThreadPoolBuilder.scheduledPool().build();
		ExceptionTask newTask = new ExceptionTask();
		Runnable wrapTask = ThreadPoolUtil.safeRunnable(newTask);
		executor.scheduleAtFixedRate(wrapTask, 0, 100, TimeUnit.MILLISECONDS);

		ThreadUtil.sleep(500);
		assertThat(newTask.counter.get()).isGreaterThan(2);
		System.out.println("newTask-------actual run:" + newTask.counter.get());
		ThreadPoolUtil.gracefulShutdown(executor, 1000);

	}

	static class ExceptionTask implements Runnable {
		public AtomicInteger counter = new AtomicInteger(0);

		@Override
		public void run() {
			counter.incrementAndGet();
			throw new RuntimeException("fail");
		}

	}

	static class Task implements Runnable {
		private final Logger logger;

		private int runTime = 0;

		private final int sleepTime;

		Task(Logger logger, int sleepTime, int runTime) {
			this.logger = logger;
			this.sleepTime = sleepTime;
			this.runTime = runTime;
		}

		@Override
		public void run() {
			System.out.println("start task");
			if (runTime > 0) {
				long start = System.currentTimeMillis();
				while ((System.currentTimeMillis() - start) < runTime) {
				}
			}

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				logger.warn("InterruptedException");
			}
		}
	}
}