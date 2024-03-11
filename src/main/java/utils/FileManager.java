package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileManager {
    public static final String HOME_PATH = "/";
    public static final String DEFAULT_URL = "/index.html";
    public static final String STATIC_DEFAULT_DIRECTORY_PATH = "./src/main/resources/static";

    public static String getRequestHeader(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        StringBuilder requestHeaderBuilder = new StringBuilder();
        while (!(line = br.readLine()).isEmpty()) {
            requestHeaderBuilder.append(line).append("\n");
        }
        return requestHeaderBuilder.toString();
    }

    public static String getUrl(String requestHeader) {
        return requestHeader.split("\n")[0]
                .split(" ")[1];
    }

    public static String getStaticFilePath(String url) {
        if (url.equals(HOME_PATH)) {
            url = DEFAULT_URL;
        }
        return STATIC_DEFAULT_DIRECTORY_PATH + url;
    }

    public static String getFileExtension(String filePath) {
        int lastIndexOfDot = filePath.lastIndexOf(".");
        int lastIndexOfSlash = filePath.lastIndexOf("/");

        if (lastIndexOfDot == -1 || lastIndexOfDot < lastIndexOfSlash) {
            return ""; // 확장자가 없음을 나타냅니다.
        }

        return filePath.substring(lastIndexOfDot + 1);
    }
}
