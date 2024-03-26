package webserver.requesthandler.http.message;

import java.net.HttpCookie;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import webserver.requesthandler.http.HttpConst;

public class Headers {
    private final Map<String, String> store;

    public Headers(Map<String, String> store) {
        this.store = store;
    }

    public Headers() {
        this.store = new HashMap<>();
    }

    public void put(String key, String value) {
        store.put(key, value);
    }

    public HttpCookie getCookie(String cookieName) {
        String cookieValues = store.get(HttpConst.HEADER_COOKIE);
        if (cookieValues == null) {
            return null;
        }
        String[] cookieParts = cookieValues.split(HttpConst.COOKIE_VALUE_DELIMITER + HttpConst.SPACE_REGEX);
        return Arrays.stream(cookieParts)
                .map(cookiePart -> HttpCookie.parse(cookiePart).get(0))
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst()
                .orElse(null);
    }

    public boolean hasContentType(String contentType) {
        String contentTypeValue = store.get(HttpConst.HEADER_CONTENT_TYPE);
        if (contentTypeValue == null) {
            return false;
        }
        return contentTypeValue.contains(contentType);
    }

    public String getContentType() {
        return store.get(HttpConst.HEADER_CONTENT_TYPE);
    }

    public int getContentLength() {
        String contentLength = store.get(HttpConst.HEADER_CONTENT_LENGTH);
        if (contentLength == null) {
            return 0;
        }
        return Integer.parseInt(contentLength);
    }


    public String get(String headerKey) {
        return store.get(headerKey);
    }


    public boolean containsKey(String key) {
        return store.containsKey(key);
    }

    public Map<String, String> getStore() {
        return this.store;
    }
}
