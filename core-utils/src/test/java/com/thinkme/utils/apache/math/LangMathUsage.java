package com.thinkme.utils.apache.math;

import org.apache.commons.lang3.*;
import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.lang3.math.NumberUtils;

public class LangMathUsage {
	public static void main(String[] args) {
		demoFraction();
		demoNumberUtils();
		demoNumberRange();
		demoRandomUtils();
	}

	public static void demoFraction() {
		System.out.println(StringUtils.center(" demoFraction ", 30, "="));
		Fraction myFraction = Fraction.getFraction(144, 90);
// Fraction myFraction = Fraction.getFraction("1 54/90");

		System.out.println("144/90 to proper: " + myFraction.toProperString());
		System.out.println("144/90 as double: " + myFraction.doubleValue());
		System.out.println("144/90 reduced: " + myFraction.reduce());
		System.out.println("144/90 reduced proper: " + myFraction.reduce().toProperString());
		System.out.println();
	}

	public static void demoNumberUtils() {
		System.out.println(StringUtils.center(" demoNumberUtils ", 30, "="));
		System.out.println("Is 0x3F a number? "
				+ StringUtils.capitalize(BooleanUtils.toStringYesNo(NumberUtils.isNumber("0x3F"))) + ".");
		double[] array = {1.0, 3.4, 0.8, 7.1, 4.6};
		double max = NumberUtils.max(array);
		double min = NumberUtils.min(array);
		String arrayStr = ArrayUtils.toString(array);
		System.out.println("Max of " + arrayStr + " is: " + max);
		System.out.println("Min of " + arrayStr + " is: " + min);
		System.out.println();
	}

	public static void demoNumberRange() {
		System.out.println(StringUtils.center(" demoNumberRange ", 30, "="));
		Range<Double> normalScoreRange = Range.between (90.0, 120.0);
		double score1 = 102.5;
		double score2 = 79.9;
		System.out.println("Normal score range is: " + normalScoreRange);
		System.out.println("Is "
				+ score1
				+ " a normal score? " + StringUtils
				.capitalize(BooleanUtils.toStringYesNo(normalScoreRange.contains(score1))) + ".");
		System.out.println("Is " + score2 + " a normal score? " + StringUtils
				.capitalize(BooleanUtils.toStringYesNo(normalScoreRange.contains(score2))) + ".");
		System.out.println();
	}

	public static void demoRandomUtils() {
		System.out.println(StringUtils.center(" demoRandomUtils ", 30, "="));
		for (int i = 0; i < 5; i++) {
			System.out.println(RandomUtils.nextInt(1,10));
		}
		System.out.println();
	}
}

