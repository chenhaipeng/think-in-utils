package com.thinkme.utils.google.predicate;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * google guava 断言用于过滤集合
 *
 * @author thinkme
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2017/07/22 上午11:43
 */
public class predicateTest {

	@Test
	public void test() {


		List<User> users = new ArrayList<User>();
		users.add(new User("chang", 24));
		users.add(new User("chen", 26));
		users.add(new User("sun", 24));

		//保留age不为26的User
		Predicate<User> predicate1 = new Predicate<User>() {
			public boolean apply(User user) {
				if (user.getAge() != 26) {
					return true;
				}
				return false;
			}
		};


		//保留userName 是 chang 的user
		Predicate<User> predicate2 = new Predicate<User>() {
			public boolean apply(User user) {
				return Objects.equals(user.getUserName(), "chang");
			}
		};

		//保留age不为 26 以及 userName 是 chang 的User
		Predicate<User> predicate1_and_predicate2 = Predicates.and(predicate1, predicate2);

		//保留age不为26 或 userName 是 chang的User
		Predicate<User> predicate1_or_predicate2 = Predicates.or(predicate1, predicate2);

		//与predicate1条件相反
		Predicate<User> notpredicate1 = Predicates.not(predicate1);

		//List<User> filteredUsers = Lists.newArrayList(Iterators.filter(users.iterator(), predicate1));
		List<User> filteredUsers1 = Lists.newArrayList(Iterables.filter(users, predicate1));
		List<User> filteredUsers2 = Lists.newArrayList(Iterables.filter(users, predicate2));
		List<User> filteredUsers1and2 = Lists.newArrayList(Iterables.filter(users, predicate1_and_predicate2));
		List<User> filteredUsers1or2 = Lists.newArrayList(Iterables.filter(users, predicate1_or_predicate2));

		List<User> filteredUsersNot1 = Lists.newArrayList(Iterables.filter(users, notpredicate1));

		System.out.println("result size for filteredUsers1: " + filteredUsers1.size());          //2->  chang sun
		System.out.println("result size for filteredUsers2:  " + filteredUsers2.size());         //1-> chang
		System.out.println("result size for filteredUsers1and2:  " + filteredUsers1and2.size()); //1-> chang
		System.out.println("result size for filteredUsers1or2:  " + filteredUsers1or2.size());   //2-> chang sun

		System.out.println("result size for filteredUsersNot1:  " + filteredUsersNot1.size());   //1-> chen
	}
}
