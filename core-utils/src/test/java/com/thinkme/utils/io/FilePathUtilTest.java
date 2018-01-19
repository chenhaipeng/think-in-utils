package com.thinkme.utils.io;

import com.google.common.io.Files;
import com.thinkme.utils.base.Platforms;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class FilePathUtilTest {
    @Test
    public void getRooPath() throws Exception {
        System.out.println(new File("codegen.xsd").getAbsoluteFile());
        System.out.println(FilePathUtil.getClassRootPath());
        System.out.println(FilePathUtil.getProjectRootPath());
    }


    char sep = Platforms.FILE_PATH_SEPARATOR_CHAR;

    @Test
    public void pathName() {
        String filePath = FilePathUtil.contact(sep + "abc", "ef");
        assertThat(filePath).isEqualTo(FilePathUtil.normalizePath("/abc/ef"));

        String filePath2 = FilePathUtil.contact(sep + "stuv" + sep, "xy");
        assertThat(filePath2).isEqualTo(FilePathUtil.normalizePath("/stuv/xy"));

        assertThat(FilePathUtil.simplifyPath("../dd/../abc")).isEqualTo("../abc");
        assertThat(FilePathUtil.simplifyPath("../../dd/../abc")).isEqualTo("../../abc");
        assertThat(FilePathUtil.simplifyPath("./abc")).isEqualTo("abc");

        assertThat(FilePathUtil.getParentPath(FilePathUtil.normalizePath("/abc/dd/efg/")))
                .isEqualTo(FilePathUtil.normalizePath("/abc/dd/"));

        assertThat(FilePathUtil.getParentPath(FilePathUtil.normalizePath("/abc/dd/efg.txt")))
                .isEqualTo(FilePathUtil.normalizePath("/abc/dd/"));
    }

    @Test
    public void getJarPath() {
        System.out.println("the jar file contains Files.class" + FilePathUtil.getJarPath(Files.class));
        assertThat(FilePathUtil.getJarPath(Files.class)).endsWith("guava-20.0.jar");
    }

//    start

//    end
}
