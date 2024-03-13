package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileManager {
    public static final String DEFAULT_FILE = "/index.html";
    public static final String STATIC_DEFAULT_DIRECTORY_PATH = "./src/main/resources/static";

    public static final String FILE_EXTENSION_MARKER = ".";
    public static final String FILE_SEPARATOR = "/";
    public static final String NO_FILE_EXTENSION = "";


    public static String getStaticFilePath(String url) {
        String filePath = STATIC_DEFAULT_DIRECTORY_PATH + url;
        if (url.contains(FILE_EXTENSION_MARKER)) {
            return filePath;
        }
        return filePath + DEFAULT_FILE;
    }

    public static String getFileExtension(String filePath) {
        int lastIndexOfDot = filePath.lastIndexOf(FILE_EXTENSION_MARKER);
        int lastIndexOfSlash = filePath.lastIndexOf(FILE_SEPARATOR);

        if (lastIndexOfDot == -1 || lastIndexOfDot < lastIndexOfSlash) {
            return NO_FILE_EXTENSION; // 확장자가 없음을 나타냅니다.
        }

        return filePath.substring(lastIndexOfDot + 1);
    }

    public static byte[] getFileBody(String filePath) throws IOException {
        byte[] body;
        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file)) {
            body = new byte[(int) file.length()];
            fis.read(body);
        }
        return body;
    }
}
