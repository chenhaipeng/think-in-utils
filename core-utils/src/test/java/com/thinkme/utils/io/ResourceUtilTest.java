package com.thinkme.utils.io;

import com.google.common.io.Files;
import org.junit.Test;

import java.io.IOException;
import java.util.jar.JarFile;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceUtilTest {

    @Test
    public void test() throws IOException {
        // getResoruce
        assertThat(ResourceUtil.toString("test.txt")).contains("ABCDEFG");
        assertThat(ResourceUtil.toString(ResourceUtilTest.class, "/test.txt")).contains("ABCDEFG");
        assertThat(ResourceUtil.toLines("test.txt")).containsExactly("ABCDEFG", "ABC");
        assertThat(ResourceUtil.toLines(ResourceUtilTest.class, "/test.txt")).containsExactly("ABCDEFG", "ABC");

        // getResoruce 处理重复的资源
        System.out.println(ResourceUtil.asUrl("META-INF/MANIFEST.MF"));
        System.out.println("getResource==" + ResourceUtil.toString("META-INF/MANIFEST.MF"));
        assertThat(ResourceUtil.toString("META-INF/MANIFEST.MF")).contains("Manifest");

        // getResources
        assertThat(ResourceUtil.getResources("META-INF/MANIFEST.MF").size()).isGreaterThan(1);

        System.out.println(ResourceUtil.getResources("META-INF/MANIFEST.MF"));

        assertThat(ResourceUtil.getResources("META-INF/MANIFEST.MF", ResourceUtilTest.class.getClassLoader()).size())
                .isGreaterThan(1);

    }

    @Test
    public void resourceNameTest() throws IOException {
        JarFile guavaFile = new JarFile(FilePathUtil.getJarPath(Files.class));
        System.out.println(FilePathUtil.getJarPath(Files.class));
        assertThat(guavaFile.getEntry("META-INF/MANIFEST.MF")).isNotNull();
        assertThat(guavaFile.getEntry("/META-INF/MANIFEST.MF")).isNull();
        guavaFile.close();
    }


}
