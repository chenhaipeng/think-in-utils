package com.thinkme.utils.collection;

import com.google.common.collect.Ordering;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectionUtilTest {

	@Test
	public void test() {
		List<String> list1 = com.thinkme.utils.collection.ListUtil.newArrayList();

		List<String> list2 = com.thinkme.utils.collection.ListUtil.newArrayList("a", "b", "c");
		List<String> list3 = com.thinkme.utils.collection.ListUtil.newArrayList("a");

		Set<String> set1 = SetUtil.newSortedSet();
		set1.add("a");
		set1.add("b");
		set1.add("c");

		Set<String> set2 = SetUtil.newSortedSet();
		set2.add("a");

		Assertions.assertThat(com.thinkme.utils.collection.CollectionUtil.isEmpty(list1)).isTrue();
		assertThat(com.thinkme.utils.collection.CollectionUtil.isEmpty(null)).isTrue();
		assertThat(com.thinkme.utils.collection.CollectionUtil.isEmpty(list2)).isFalse();

		assertThat(com.thinkme.utils.collection.CollectionUtil.isNotEmpty(list1)).isFalse();
		assertThat(com.thinkme.utils.collection.CollectionUtil.isNotEmpty(null)).isFalse();
		assertThat(com.thinkme.utils.collection.CollectionUtil.isNotEmpty(list2)).isTrue();

		assertThat(com.thinkme.utils.collection.CollectionUtil.getFirst(list2)).isEqualTo("a");
		assertThat(com.thinkme.utils.collection.CollectionUtil.getLast(list2)).isEqualTo("c");

		assertThat(com.thinkme.utils.collection.CollectionUtil.getFirst(set1)).isEqualTo("a");
		assertThat(com.thinkme.utils.collection.CollectionUtil.getLast(set1)).isEqualTo("c");

		assertThat(com.thinkme.utils.collection.CollectionUtil.getFirst(list3)).isEqualTo("a");
		assertThat(com.thinkme.utils.collection.CollectionUtil.getLast(list3)).isEqualTo("a");

		assertThat(com.thinkme.utils.collection.CollectionUtil.getFirst(set2)).isEqualTo("a");
		assertThat(com.thinkme.utils.collection.CollectionUtil.getLast(set2)).isEqualTo("a");

		assertThat(com.thinkme.utils.collection.CollectionUtil.getFirst(list1)).isNull();
		assertThat(com.thinkme.utils.collection.CollectionUtil.getFirst(null)).isNull();
		assertThat(com.thinkme.utils.collection.CollectionUtil.getLast(list1)).isNull();
		assertThat(com.thinkme.utils.collection.CollectionUtil.getLast(null)).isNull();
	}

	@Test
	public void minAndMax() {
		List<Integer> list = com.thinkme.utils.collection.ListUtil.newArrayList(4, 1, 9, 100, 20, 101, 40);

		assertThat(com.thinkme.utils.collection.CollectionUtil.min(list)).isEqualTo(1);
		assertThat(com.thinkme.utils.collection.CollectionUtil.min(list, Ordering.natural())).isEqualTo(1);
		assertThat(com.thinkme.utils.collection.CollectionUtil.max(list)).isEqualTo(101);
		assertThat(com.thinkme.utils.collection.CollectionUtil.max(list, Ordering.natural())).isEqualTo(101);

		assertThat(com.thinkme.utils.collection.CollectionUtil.minAndMax(list).getFirst()).isEqualTo(1);
		assertThat(com.thinkme.utils.collection.CollectionUtil.minAndMax(list).getSecond()).isEqualTo(101);

		assertThat(com.thinkme.utils.collection.CollectionUtil.minAndMax(list, Ordering.natural()).getFirst()).isEqualTo(1);
		assertThat(com.thinkme.utils.collection.CollectionUtil.minAndMax(list, Ordering.natural()).getSecond()).isEqualTo(101);

	}

	@Test
	public void listCompare() {
		List<String> list1 = com.thinkme.utils.collection.ArrayUtil.asList("d", "a", "c", "b", "e", "i", "g");
		List<String> list2 = com.thinkme.utils.collection.ArrayUtil.asList("d", "a", "c", "b", "e", "i", "g");

		List<String> list3 = com.thinkme.utils.collection.ArrayUtil.asList("d", "c", "a", "b", "e", "i", "g");
		List<String> list4 = com.thinkme.utils.collection.ArrayUtil.asList("d", "a", "c", "b", "e");
		List<String> list5 = com.thinkme.utils.collection.ArrayUtil.asList("d", "a", "c", "b", "e", "i", "g", "x");

		assertThat(com.thinkme.utils.collection.CollectionUtil.elementsEqual(list1, list1)).isTrue();
		assertThat(com.thinkme.utils.collection.CollectionUtil.elementsEqual(list1, list2)).isTrue();

		assertThat(com.thinkme.utils.collection.CollectionUtil.elementsEqual(list1, list3)).isFalse();
		assertThat(com.thinkme.utils.collection.CollectionUtil.elementsEqual(list1, list4)).isFalse();
		assertThat(com.thinkme.utils.collection.CollectionUtil.elementsEqual(list1, list5)).isFalse();
	}

	@Test
	public void topNAndBottomN() {
		List<Integer> list = com.thinkme.utils.collection.ArrayUtil.asList(3, 5, 7, 4, 2, 6, 9);
		
		assertThat(com.thinkme.utils.collection.CollectionUtil.topN(list, 3)).containsExactly(9,7,6);
		assertThat(com.thinkme.utils.collection.CollectionUtil.topN(list, 3, Ordering.natural().reverse())).containsExactly(2,3,4);
		assertThat(com.thinkme.utils.collection.CollectionUtil.bottomN(list, 3)).containsExactly(2,3,4);
		assertThat(com.thinkme.utils.collection.CollectionUtil.bottomN(list, 3, Ordering.natural().reverse())).containsExactly(9,7,6);
	}
}
