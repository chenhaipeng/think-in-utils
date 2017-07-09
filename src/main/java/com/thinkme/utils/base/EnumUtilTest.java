package com.thinkme.utils.base;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumUtilTest {

	public enum Options {
		A, B, C, D;
	}

	/**
	 * 将enum 的ordinal 2^ 再相加
	 * 应用场景？，未明
	 */
	@Test
	public void test() {
		System.out.println(EnumUtil.generateBits(Options.class, Options.A));
		assertThat(EnumUtil.generateBits(Options.class, Options.A)).isEqualTo(1);
		assertThat(EnumUtil.generateBits(Options.class, Options.A, Options.B,Options.D)).isEqualTo(11);

		assertThat(EnumUtil.generateBits(Options.class, com.thinkme.utils.collection.ListUtil.newArrayList(Options.A))).isEqualTo(1);
		assertThat(EnumUtil.generateBits(Options.class, com.thinkme.utils.collection.ListUtil.newArrayList(Options.A, Options.B))).isEqualTo(3);

		assertThat(EnumUtil.processBits(Options.class, 3)).hasSize(2).containsExactly(Options.A, Options.B);
		assertThat(EnumUtil.processBits(Options.class,
				EnumUtil.generateBits(Options.class, Options.A, Options.C, Options.D))).hasSize(3)
						.containsExactly(Options.A, Options.C, Options.D);

	}


}
