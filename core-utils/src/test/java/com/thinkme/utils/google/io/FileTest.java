package com.thinkme.utils.google.io;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author thinkme
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2017/07/15 上午1:23
 */
public class FileTest {

	@Test
	public void readLine() throws IOException {

		// 文件 写/新增内容     完全不用去关心打开打开流/关闭流
		File file = new File("1.txt");
		String hamletQuoteStart = "To be, or not to be";
		Files.write(hamletQuoteStart, file, Charsets.UTF_8);
		String hamletQuoteEnd = "that is a question";
		Files.append(hamletQuoteEnd, file, Charsets.UTF_8);

		// 将文件内容一行一行读取出来

		List<String> expectedLines = Lists.newArrayList("The quick brown", " fox jumps over", " the lazy dog");
		List<String> readLines = Files.readLines(file, Charsets.UTF_8);
		System.out.printf(readLines.toString());
	}
}
