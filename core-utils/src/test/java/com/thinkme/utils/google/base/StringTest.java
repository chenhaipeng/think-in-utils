package com.thinkme.utils.google.base;

import com.google.common.base.Strings;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试google String 工具类相关
 * @author thinkme
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2017/07/08 下午10:03
 */
public class StringTest {

	@Test
	public void test(){
		assertThat(Strings.isNullOrEmpty("")).isTrue();
		assertThat(Strings.nullToEmpty(null).equals(""));
		assertThat(Strings.nullToEmpty("a").equals("a"));
		assertThat(Strings.emptyToNull("")).isNull();
		assertThat(Strings.emptyToNull("a").equals("a"));

		Strings.padStart("7", 3, '0');//"007"
		Strings.padStart("2010", 3, '0');//"2010"
		Strings.padEnd("4.", 5, '0');//"4.000"
		Strings.padEnd("2010", 3, '!');//"2010"
		Strings.repeat("hey", 3);//"heyheyhey"


	}
}
