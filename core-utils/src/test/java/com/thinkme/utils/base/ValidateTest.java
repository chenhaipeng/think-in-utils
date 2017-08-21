package com.thinkme.utils.base;

import org.apache.commons.lang3.Validate;
import org.junit.Test;

/**
 * @author thingme
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2017/07/08 下午1:19
 */
public class ValidateTest {

	@Test(expected = NullPointerException.class)
	public void test(){
		String s = null;
		Validate.notEmpty(s);
	}
}
