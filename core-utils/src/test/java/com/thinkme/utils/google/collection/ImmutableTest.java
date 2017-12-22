package com.thinkme.utils.google.collection;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import static com.google.common.base.Predicates.equalTo;
import static com.google.common.base.Predicates.or;
import static com.google.common.collect.Collections2.filter;

/**
 * 测试google Immutable类
 *
 * @author thinkme
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2017/07/08 下午6:04
 */
public class ImmutableTest {

    public static Predicate<String> lengthLessThan(final int length) {
        return new LengthLessThanPredicate(length);
    }

    @Test
    public void testImmutableList() {
        ImmutableList<String> of = ImmutableList.of("a", "b", "c", "d");
        System.out.println(of);
        ImmutableMap<String, String> map = ImmutableMap.of("key1", "value1", "key2", "value2");
        System.out.println(map);
    }

    @Test
    public void testFilter() {
        ImmutableList<String> names = ImmutableList.of("Aleksander", "Jaran", "Integrasco", "Guava", "Java");
        Iterable<String> filtered = filter(names, or(or(equalTo("Aleksander"), equalTo("Jaran")), lengthLessThan(5)));
        System.out.println(filtered);

    }

    /**
     * 测试google 函数式编程
     */
    public void testFuntions() {


    }

    private static class LengthLessThanPredicate implements Predicate<String> {
        private final int length;

        private LengthLessThanPredicate(final int length) {
            this.length = length;
        }

        public boolean apply(final String s) {
            return s.length() < length;
        }
    }

}
