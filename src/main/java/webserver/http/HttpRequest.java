package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    public static final String REQUEST_LINE_DELIMITER = " ";
    public static final String QUERY_COMMAND_START = "?";
    public static final String QUERY_DELIMITER = "&";
    public static final String PARAM_DELIMITER = "=";
    public static final String CONTENT_LENGTH_LABEL = "Content-Length";
    public static final String EMPTY_STRING = "";
    public static final String HEADER_DELIMITER = ":";
    public static final int PARAMS_LENGTH = 2;
    private String method;
    private String path;
    private Map<String, String> params;
    private Map<String, String> headers;
    private String body;

    public HttpRequest() {
    }

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String[] requestLineParts = extractRequestLineParts(br);
        this.method = requestLineParts[0];
        String url = requestLineParts[1];
        this.path = extractPath(url);
        this.params = extractQueryParams(url);
        this.headers = extractHeaders(br);
        this.body = extractBody(br);
    }

    private String[] extractRequestLineParts(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        return requestLine.split(REQUEST_LINE_DELIMITER);
    }

    private String extractPath(String url) {
        if (url.contains(QUERY_COMMAND_START)) {
            return url.substring(0, url.indexOf(QUERY_COMMAND_START));
        }
        return url;
    }

    private Map<String, String> extractQueryParams(String url) {
        Map<String, String> queryParams = new HashMap<>();
        if (url.contains(QUERY_COMMAND_START)) {
            addParams(url, queryParams);
        }
        return queryParams;
    }

    private void addParams(String url, Map<String, String> queryParams) {
        String[] queries = url.substring(url.indexOf(QUERY_COMMAND_START) + 1).split(QUERY_DELIMITER);
        for (String param : queries) {
            String[] splitedParam = param.split(PARAM_DELIMITER);
            if (splitedParam.length == PARAMS_LENGTH) {
                queryParams.put(splitedParam[0], splitedParam[1]);
            }
        }
    }

    private Map<String, String> extractHeaders(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] headerParts = line.split(HEADER_DELIMITER);
            headers.put(headerParts[0].trim(), headerParts[1].trim());
        }
        return headers;
    }

    private String extractBody(BufferedReader br) throws IOException {
        if (headers.containsKey(CONTENT_LENGTH_LABEL)) {
            int contentLength = Integer.parseInt(headers.get(CONTENT_LENGTH_LABEL));
            char[] body = new char[contentLength];
            br.read(body);
            return new String(body);
        }
        return EMPTY_STRING;
    }

    public HttpCookie getCookie(String cookieName) {
        String[] cookieParts = headers.get("Cookie").split(";\\s");
        for (String cookiePart : cookieParts) {
            HttpCookie cookie = HttpCookie.parse(cookiePart).get(0);
            if (cookie.getName().equals(cookieName)) {
                return cookie;
            }
        }
        return null;
    }

    public String getHeader(String headerKey) {
        return headers.get(headerKey);
    }

    public String getParameter(String paramKey) {
        return params.get(paramKey);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getBody() {
        return body;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
