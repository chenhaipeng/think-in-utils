package com.thinkme.utils.concurrent.threadpool;

import com.thinkme.utils.concurrent.ThreadUtil;
import org.junit.Test;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class QueuableCachedThreadPoolTest {

	public static class LongRunTask implements Runnable {
		@Override
		public void run() {
			ThreadUtil.sleep(5, TimeUnit.SECONDS);
		}
	}

	@Test
	public void test() {
		QueuableCachedThreadPool threadPool = null;
		try {
			threadPool = ThreadPoolBuilder.queuableCachedPool().setMinSize(0).setMaxSize(10).setQueueSize(10).build();
			// 线程满
			for (int i = 0; i < 11; i++) {
				threadPool.submit(new LongRunTask());
			}

			assertThat(threadPool.getActiveCount()).isEqualTo(10);
			//大于线程池数据进queue
			assertThat(threadPool.getQueue().size()).isEqualTo(1);

			// queue 满
			for (int i = 0; i < 10; i++) {
				threadPool.submit(new LongRunTask());
			}
			assertThat(threadPool.getActiveCount()).isEqualTo(10);
			assertThat(threadPool.getQueue().size()).isEqualTo(10);

			// 爆
			try {
				threadPool.submit(new LongRunTask());
				fail("should fail before");
			} catch (Throwable t) {
				assertThat(t).isInstanceOf(RejectedExecutionException.class);
			}

		} finally {
			ThreadPoolUtil.gracefulShutdown(threadPool, 1000);
		}
	}

}
