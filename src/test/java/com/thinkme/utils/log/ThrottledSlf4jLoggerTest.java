package com.thinkme.utils.log;

import com.thinkme.utils.time.ClockUtil;
import com.thinkme.utils.time.ClockUtil.DummyClock;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.test.log.LogbackListAppender;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

//针对于时间间隔而不是打印的key
public class ThrottledSlf4jLoggerTest {

	@Test
	public void test() {
		try {
			DummyClock clock = ClockUtil.useDummyClock();
			Logger realLogger = LoggerFactory.getLogger(ThrottledSlf4jLoggerTest.class);
			LogbackListAppender appender = LogbackListAppender.create(ThrottledSlf4jLoggerTest.class);
			// 间隔10毫秒
			ThrottledSlf4jLogger logger = new ThrottledSlf4jLogger(realLogger, 10, TimeUnit.MILLISECONDS);

			logger.warn("haha");
			assertThat(appender.getLogsCount()).isEqualTo(1);

			// still 1
			logger.warn("haha {}", 1);
			assertThat(appender.getLogsCount()).isEqualTo(1);

			//other
			logger.warn("test {}",1);
			assertThat(appender.getLogsCount()).isEqualTo(1);

			// still 1
			logger.error("haha {}", 1);
			assertThat(appender.getLogsCount()).isEqualTo(1);
			// still 1
			clock.increaseTime(5);
			logger.error("haha, {} {} {}", 1, 2, 3);
			assertThat(appender.getLogsCount()).isEqualTo(1);

			// 10ms pass
			clock.increaseTime(5);
			logger.warn("haha");
			assertThat(appender.getLogsCount()).isEqualTo(2);

			// still 2
			logger.warn("haha {} {} {}", 1, 2, 3);
			assertThat(appender.getLogsCount()).isEqualTo(2);

			// still 2
			clock.increaseTime(7);
			logger.warn("haha");
			assertThat(appender.getLogsCount()).isEqualTo(2);

		} finally {
			ClockUtil.useDefaultClock();
		}

	}

}
