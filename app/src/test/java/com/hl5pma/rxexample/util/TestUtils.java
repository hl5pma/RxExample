package com.hl5pma.rxexample.util;

import java.io.File;

public class TestUtils {

    // build/intermediates/classes/test/{$buildType}/{$package}/resources 로 resources 디렉토리가 잡히기 때문에 resources 디렉토리 찾아줌
    private static final File RESOURCES_DIR = new File(new File(TestUtils.class.getResource("").getPath())
            .getParentFile().getParentFile().getParentFile().getParentFile().getParentFile()
            .getParentFile().getParentFile().getParentFile().getParentFile(), "src/test/resources");

    private TestUtils() {
    }

    public static File getResourceFile(String fileName) {
        return new File(RESOURCES_DIR, fileName);
    }
}
