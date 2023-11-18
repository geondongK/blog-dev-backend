package blogservices.blogdevbackend.global.common;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// S3 업로드 설정
public class S3Utils {
    private static final String FILE_EXTENSION_SEPARATOR = ".";
    private static final String TIME_SEPARATOR = "_";
    // private static final String CATEGORY_PREFIX = "/";

    public static String buildFileName(String originalFileName) {
        String now = String.valueOf(System.currentTimeMillis());

        return now + FILE_EXTENSION_SEPARATOR + originalFileName;
    }
}
