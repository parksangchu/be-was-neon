package utils;

import java.io.IOException;
import java.util.Objects;

public class FileManager {
    public static final String FILE_EXTENSION_MARKER = ".";
    public static final String FILE_SEPARATOR = "/";
    public static final String NO_FILE_EXTENSION = "";
    public static final String TEMPLATES_DIRECTORY = "/templates";
    public static final String INDEX_HTML_FILE = "/index.html";

    public static boolean isFile(String requestPath) {
        return requestPath.contains(FILE_EXTENSION_MARKER);
    }

    public static String getFileExtension(String requestPath) {
        int lastIndexOfDot = requestPath.lastIndexOf(FILE_EXTENSION_MARKER);
        int lastIndexOfSlash = requestPath.lastIndexOf(FILE_SEPARATOR);

        if (lastIndexOfDot == -1 || lastIndexOfDot < lastIndexOfSlash) {
            return NO_FILE_EXTENSION; // 확장자가 없음을 나타냅니다.
        }

        return requestPath.substring(lastIndexOfDot + 1);
    }

    public static byte[] getStaticResource(String requestPath) throws IOException {
        return Objects.requireNonNull(FileManager.class.getResourceAsStream(requestPath))
                .readAllBytes();
    }

    public static byte[] getTemplate(String requestPath) throws IOException {
        return Objects.requireNonNull(
                        FileManager.class.getResourceAsStream(TEMPLATES_DIRECTORY + requestPath + INDEX_HTML_FILE))
                .readAllBytes();
    }
}
