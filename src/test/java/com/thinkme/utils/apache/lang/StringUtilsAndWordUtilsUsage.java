package com.thinkme.utils.apache.lang;

import org.apache.commons.lang3.StringUtils;

public class StringUtilsAndWordUtilsUsage {
	public static void main(String[] args) {
// data setup
		String str1 = "";
		String str2 = " ";
		String str3 = "\t";
		String str4 = null;
		String str5 = "123";
		String str6 = "ABCDEFG";
		String str7 = "It feels good to use Jakarta Commons.\r\n";


		System.out.println("==============================");
		System.out.println("Is str1 blank? " + StringUtils.isBlank(str1));
		System.out.println("Is str2 blank? " + StringUtils.isBlank(str2));
		System.out.println("Is str3 blank? " + StringUtils.isBlank(str3));
		System.out.println("Is str4 blank? " + StringUtils.isBlank(str4));
// check for numerics
		System.out.println("==============================");
		System.out.println("Is str5 numeric? " + StringUtils.isNumeric(str5));
		System.out.println("Is str6 numeric? " + StringUtils.isNumeric(str6));
// reverse strings / whole words
		System.out.println("==============================");
		System.out.println("str6: " + str6);
		System.out.println("str6 reversed: " + StringUtils.reverse(str6));
		System.out.println("str7: " + str7);
		String str8 = StringUtils.chomp(str7);
		str8 = StringUtils.reverseDelimited(str8, ' ');
		System.out.println("str7 reversed whole words : \r\n" + str8);
// build header (useful to print log messages that are easy to locate)
		System.out.println("==============================");
		System.out.println("print header:");
		String padding = StringUtils.repeat("=", 50);
		String msg = StringUtils.center(" Customised Header ", 50, "%");
		Object[] raw = new Object[]{padding, msg, padding};
		String header = StringUtils.join(raw, "\r\n");
		System.out.println(header);
	}
}