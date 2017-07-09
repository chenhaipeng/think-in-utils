package com.thinkme.utils.io;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;

import org.junit.Test;
import com.thinkme.utils.text.Charsets;

public class IOUtilTest {

	@Test
	public void read() throws IOException {
		assertThat(IOUtil.toString(ResourceUtil.asStream("test.txt"))).isEqualTo("ABCDEFG\nABC");
		assertThat(IOUtil.toLines(ResourceUtil.asStream("test.txt"))).hasSize(2).containsExactly("ABCDEFG", "ABC");
	}

	@Test
	public void write() throws IOException {
		com.thinkme.utils.io.type.StringBuilderWriter sw = new com.thinkme.utils.io.type.StringBuilderWriter();
		IOUtil.write("hahahaha", sw);
		assertThat(sw.toString()).isEqualTo("hahahaha");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		IOUtil.write("hahahaha", out);
		assertThat(new String(out.toByteArray(), Charsets.UTF_8)).isEqualTo("hahahaha");

		IOUtil.closeQuietly(out);
		IOUtil.closeQuietly((Closeable) null);
	}

}
