package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestHeaderManager {
    public static final String REQUEST_HEADER_FILED_SEPARATOR = " ";
    public static final String REQUEST_HEADER_LINE_SEPARATOR = "\n";

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
}
