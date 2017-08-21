package com.thinkme.utils.google.sort;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.*;
import org.junit.Test;

import java.util.*;

/**
 * @author thinkme
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2017/07/22 下午12:29
 */
public class ComparatorTest {

	@Test
	public void test() {
		Map<String, UserGuava> map = Maps.newHashMap();
		List<UserGuava> list = Lists.newArrayList();
		UserGuava user = new UserGuava();
		user.setAge(1);
		user.setName("JetBrains");
		map.put("1", user);
		list.add(user);
		user = new UserGuava();
		user.setAge(2);
		user.setName("Apple");
		list.add(user);
		map.put("2", user);
		user = new UserGuava();
		user.setAge(3);
		user.setName("Telerik");
		list.add(user);
		map.put("3", user);
		user = new UserGuava();
		user.setAge(4);
		user.setName("Google");
		list.add(user);
		map.put("4", user);
		user = new UserGuava();
		user.setAge(5);
		user.setName("Twitter");
		list.add(user);
		map.put("5", user);
		user = new UserGuava();
		user.setAge(2);
		user.setName("Facebook");
		list.add(user);
		map.put("2", user);
		user = new UserGuava();
		user.setAge(1);
		user.setName("Youtube");
		map.put("1", user);
		list.add(user);
		Predicate<UserGuava> preFilter = new Predicate<UserGuava>() {
			@Override
			public boolean apply(UserGuava user) {
				return user.getAge() > 2;
			}
		};
//		for (Multiset.Entry<String, UserGuava> e : map.entrySet()) {
//			if (preFilter.apply(e.getValue())) {
//				UserGuava u= (UserGuava)e.getValue();
//				System.out.println("Map-UserGuava="+u.getName()+","+u.getAge());
//			}
//		}
		System.out.println("-------------------你懂的------------------------");
		for (UserGuava u : list) {
			if (preFilter.apply(u)) {
				System.out.println("List-UserGuava=" + u.getName() + "," + u.getAge());
			}
		}
		System.out.println("-------------------你懂的------------------------");
		Predicate<UserGuava> p1 = new Predicate<UserGuava>() {
			@Override
			public boolean apply(UserGuava user) {
				if (user == null) {
					return false;
				}
				if (Objects.equals(user.getAge(), 1)) {
					return false;
				}
				return true;
			}
		};
		Predicate<UserGuava> p2 = new Predicate<UserGuava>() {
			@Override
			public boolean apply(UserGuava user) {
				if (user == null) {
					return false;
				}
				if (Objects.equals(user.getAge(), 2)) {
					return false;
				}
				return true;
			}
		};
		Predicate<UserGuava> unionPredicate = Predicates.and(p1, p2);
		//两种处理方式
		// List<UserGuava> newUserList = Lists.newArrayList(Iterators.filter(list.iterator(), unionPredicate));
		Collection<UserGuava> newUserList = (Collection<UserGuava>) Collections2.filter(list, unionPredicate);
		for (UserGuava u : newUserList) {
			System.out.println("List-IN-UserGuava=" + u.getName() + "," + u.getAge());
		}
		System.out.println("-------------------你懂的------------------------");
		//先按年龄，再按名称排序
		Comparator<UserGuava> userComparator = Ordering
				.from(new AgeComparator()).compound(new NameComparator());

		Collections.sort(list, userComparator);
		for (UserGuava u : list) {
			System.out.println("List-Sort-UserGuava=" + u.getName() + "," + u.getAge());
		}
	}

}
