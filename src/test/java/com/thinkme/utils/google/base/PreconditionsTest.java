package com.thinkme.utils.google.base;


import com.google.common.base.Preconditions;
import org.junit.Test;
import com.thinkme.utils.base.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 *     Preconditions是guava提供的用于进行代码校验的工具类，其中提供了许多重要的静态校验方法，
 *     用来简化我们工作或开发中对代码的校验或预 处理，能够确保代码符合我们的期望，
 *     准确的为我们显示出问题所在，接下来，我们就来学习使用Preconditions 进行代码校验。
 * @author thingme
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2017/07/08 下午1:53
 */
public class PreconditionsTest {
	//打印输出方法
	private static void print(Object obj) {
		System.out.println(String.valueOf(obj));
	}

	//测试方法
	private static boolean testMethod() {
		return 1 > 2;
	}

	//测试对象
	private static Object testObject() {
		return null;
	}

	@Test
	public void testPreconditions() {
		//checkArgument
		try {
			//校验表达式是否正确，并使用占位符输出错误信息
			Preconditions.checkArgument(1 > 2, "%s is wrong", "1 > 2");
		} catch (IllegalArgumentException e) {
			print(e.getMessage()); // 1 > 2 is wrong
		}
		//checkState
		try {
			//校验表达式是否正确，并使用占位符输出错误信息，使用方法作为表达式
			Preconditions.checkState(testMethod(), "%s is wrong", "testMethod()");
		} catch (IllegalStateException e) {
			print(e.getMessage()); // testMethod() is wrong
		}
		//checkNotNull
		try {
			//校验对象是否为空，并使用占位符输出错误信息
			Preconditions.checkNotNull(testObject(), "%s is null", "testObject()");
		} catch (NullPointerException e) {
			print(e.getMessage()); // testObject() is null
		}
		//初始化测试用list
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			list.add(i);
		}
		//checkElementIndex
		try {
			//校验元素索引是否有效 ，使用checkPositionIndex校验
			Preconditions.checkElementIndex(10, list.size());
			//在临界值size处产生异常
		} catch (IndexOutOfBoundsException e) {
			print(e.getMessage()); // index (10) must be less than size (10)
		}
		//checkPositionIndex
		try {
			//校验元素索引是否有效，使用checkPositionIndex校验
			Preconditions.checkPositionIndex(10, list.size());
			//在临界size处不产生异常
			print("checkPositionIndex does not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			print(e.getMessage()); // checkPositionIndex does not throw IndexOutOfBoundsException
		}
		//checkPositionIndexes
		try {
			//校验是否是有效的索引区间
			Preconditions.checkPositionIndexes(3, 11, list.size());
		} catch (IndexOutOfBoundsException e) {
			print(e.getMessage()); // end index (11) must not be greater than size (10)
		}
	}

	private void getName(@NotNull String xxx) {

	}


}
