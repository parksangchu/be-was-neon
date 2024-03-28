package webserver.requesthandler.httpParser;


import static webserver.requesthandler.http.HttpConst.HEADER_DELIMITER;
import static webserver.requesthandler.http.HttpConst.HTML_FORM_DATA;
import static webserver.requesthandler.http.HttpConst.PARAMS_DELIMITER;
import static webserver.requesthandler.http.HttpConst.PARAMS_LENGTH;
import static webserver.requesthandler.http.HttpConst.PARAM_DELIMITER;
import static webserver.requesthandler.http.HttpConst.QUERY_COMMAND_START;
import static webserver.requesthandler.http.HttpConst.START_LINE_DELIMITER;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import webserver.requesthandler.http.HttpConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.message.Body;
import webserver.requesthandler.http.message.Headers;
import webserver.requesthandler.http.message.Parameters;
import webserver.requesthandler.http.message.RequestLine;

public class HttpRequestParser {
    public static HttpRequest parse(InputStream in) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(in);

        String[] requestLineParts = extractRequestLineParts(bis);
        String URLWithQuery = URLDecoder.decode(requestLineParts[1], StandardCharsets.UTF_8);
        RequestLine requestLine = getRequestLine(requestLineParts, URLWithQuery);
        Headers headers = extractHeaders(bis);
        Body body = RequestBodyParser.extractBody(bis, headers);
        Parameters parameters = extractParams(URLWithQuery, headers, body);

        return new HttpRequest(requestLine, headers, body, parameters);
    }

    private static String[] extractRequestLineParts(BufferedInputStream bis) throws IOException {
        String requestLine = BISReader.readLine(bis);
        return requestLine.split(START_LINE_DELIMITER);
    }

    private static RequestLine getRequestLine(String[] requestLineParts, String queryString) {
        String method = requestLineParts[0];
        String URL = extractURL(queryString);
        return new RequestLine(method, URL);
    }

    private static String extractURL(String URLWithQuery) {
        if (URLWithQuery.contains(QUERY_COMMAND_START)) {
            return URLWithQuery.substring(0, URLWithQuery.indexOf(QUERY_COMMAND_START));
        }
        return URLWithQuery;
    }


    private static Headers extractHeaders(BufferedInputStream bis) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line;
        while ((line = BISReader.readLine(bis)) != null && !line.isEmpty()) {
            String[] headerParts = line.split(HEADER_DELIMITER);
            for (String headerPart : headerParts) {
                System.out.println("headerPart = " + headerPart);
            }
            headers.put(headerParts[0].trim(), headerParts[1].trim());
        }
        return new Headers(headers);
    }

    private static Parameters extractParams(String URLWithQuery, Headers headers, Body body) {
        Map<String, String> params = extractQueryParams(URLWithQuery);
        if (headers.hasContentType(HTML_FORM_DATA)) {
            params.putAll(extractBodyParams(headers, body));
        }
        return new Parameters(params);
    }

    private static Map<String, String> extractQueryParams(String URLWithQuery) {
        Map<String, String> params = new HashMap<>();
        if (URLWithQuery.contains(QUERY_COMMAND_START)) {
            String queryString = URLWithQuery.substring(URLWithQuery.indexOf(QUERY_COMMAND_START) + 1);
            parseParams(queryString, params);
        }
        return params;
    }

    private static Map<String, String> extractBodyParams(Headers headers, Body body) {
        Map<String, String> params = new HashMap<>();
        String contentType = headers.get(HttpConst.HEADER_CONTENT_TYPE);
        if (contentType != null && contentType.equals(HTML_FORM_DATA)) {
            String decodedBody = URLDecoder.decode(body.getStringContent(), StandardCharsets.UTF_8);
            parseParams(decodedBody, params);
        }
        return params;
    }

    private static void parseParams(String paramString, Map<String, String> paramsMap) {
        if (paramString == null || paramString.isEmpty()) {
            return;
        }

        String[] paramParts = paramString.split(PARAMS_DELIMITER);
        for (String param : paramParts) {
            String[] keyValue = param.split(PARAM_DELIMITER);
            if (keyValue.length == PARAMS_LENGTH) {
                String key = keyValue[0];
                String value = keyValue[1];
                paramsMap.put(key, value);
            }
        }
    }
}
