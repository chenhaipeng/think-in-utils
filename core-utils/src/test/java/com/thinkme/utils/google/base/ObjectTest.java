package com.thinkme.utils.google.base;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.junit.Test;

/**
 * @author thinkme
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2017/07/09 上午12:27
 */
public class ObjectTest {

	@Test
	public void test(){

		Objects.equal("a", "a"); // returns true
		Objects.equal(null, "a"); // returns false
		Objects.equal("a", null); // returns false
		Objects.equal(null, null); // returns true




		String s = MoreObjects.toStringHelper("MyObject").add("x", 1).toString();
		System.out.println(s);

	}
}
