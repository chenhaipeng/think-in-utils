package com.thinkme.utils.apache.time;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Calendar;
import java.util.Date;

public class DateTimeUsage {
	public static void main(String[] args) {
		demoDateUtils();
		demoStopWatch();
	}

	public static void demoDateUtils() {
		System.out.println(StringUtils.center(" demoDateUtils ", 30, "="));
		Date date = new Date();
		String isoDateTime = DateFormatUtils.ISO_DATETIME_FORMAT.format(date);
		String isoTime = DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(date);
		FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM");
		String customDateTime = fdf.format(date);
		System.out.println("ISO_DATETIME_FORMAT: " + isoDateTime);
		System.out.println("ISO_TIME_NO_T_FORMAT: " + isoTime);
		System.out.println("Custom FastDateFormat: " + customDateTime);
		System.out.println("Default format: " + date);
		System.out.println("Round HOUR: " + DateUtils.round(date, Calendar.HOUR));
		System.out.println("Truncate HOUR: " + DateUtils.truncate(date,
				Calendar.HOUR));
		System.out.println();
	}

	public static void demoStopWatch() {
		System.out.println(StringUtils.center(" demoStopWatch ", 30, "="));
		StopWatch sw = new StopWatch();
		sw.start();
		operationA();
		sw.stop();
		System.out.println("operationA used " + sw.getTime() + " milliseconds.");
		System.out.println();
	}

	public static void operationA() {
		try {
			Thread.sleep(999);
		} catch (InterruptedException e) { // do nothing
		}
	}
}