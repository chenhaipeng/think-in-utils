package org.springside.modules.google.collection;

import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.multiset.HashMultiSet;
import org.junit.Test;

/**
 * @author thinkme
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2017/07/10 上午12:03
 */
public class NewCollectTypeTest {

	/**
	 * 当做统计的时候非常有用
	 */
	@Test
	public void testMultiSet(){
		MultiSet set = new HashMultiSet();
		set.add("a");
		set.add("a");
		set.add("b");
		System.out.println(set.getCount("a"));
		System.out.println(set.getCount("b"));
	}

	@Test
	public void testMultimap(){

	}


}
