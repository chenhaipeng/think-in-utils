package com.thinkme.utils.google.base;

import com.google.common.base.CaseFormat;
import org.junit.Test;

/**
 * @author chenhaipeng
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2018/01/18 下午9:11
 */
public class CaseFormatTest {

    @Test
    public void testCaseFormat(){
        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, "test-data"));

        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "test_data"));
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "test_data"));
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "T_USER_DATA_VALID"));
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "T_USER_DATA_VALID"));
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, "T_USER_DATA_VALID"));
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "t_user_data_valid"));

        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "testdata"));

        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "TestData"));

        System.out.println(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, "SysUser"));
        System.out.println(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, "SysUser2"));
        System.out.println(CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_CAMEL, "SysUser3"));
        System.out.println(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "SysUser4"));

        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, "testData"));

    }

}
