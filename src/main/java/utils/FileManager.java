package utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
            List<Byte> bytes = new ArrayList<>();
            int data;
            while ((data = bis.read()) != -1) {
                bytes.add((byte) data);
            }
            body = new byte[bytes.size()];
            for (int i = 0; i < bytes.size(); i++) {
                body[i] = bytes.get(i);
            }
        }
        return body;
    }
}
