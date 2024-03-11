package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileManager {
    public static final String DEFAULT_FILE = "/index.html";
    public static final String STATIC_DEFAULT_DIRECTORY_PATH = "./src/main/resources/static";
    public static final String REQUEST_HEADER_FILED_SEPARATOR = " ";
    public static final String REQUEST_HEADER_LINE_SEPARATOR = "\n";
    public static final String FILE_EXTENSION_MARKER = ".";
    public static final String FILE_SEPARATOR = "/";
    public static final String NO_FILE_EXTENSION = "";

    public static String getRequestHeader(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        StringBuilder requestHeaderBuilder = new StringBuilder();
        while (!(line = br.readLine()).isEmpty()) {
            requestHeaderBuilder.append(line).append(REQUEST_HEADER_LINE_SEPARATOR);
        }
        return requestHeaderBuilder.toString();
    }

    public static String getUrl(String requestHeader) {
        return requestHeader.split(REQUEST_HEADER_LINE_SEPARATOR)[0]
                .split(REQUEST_HEADER_FILED_SEPARATOR)[1];
    }

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
}
