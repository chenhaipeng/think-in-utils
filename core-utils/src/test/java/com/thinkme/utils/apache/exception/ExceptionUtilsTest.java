package com.thinkme.utils.apache.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author thinkme
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2017/07/23 下午6:58
 */
public class ExceptionUtilsTest {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionUtilsTest.class);

	@Test
	public void test(){
		Throwable throwable = new Exception("xxx");
		logger.error("test",throwable);
		System.out.println("=============");
		logger.error("test2",ExceptionUtils.getRootCause(throwable));

	}
}
