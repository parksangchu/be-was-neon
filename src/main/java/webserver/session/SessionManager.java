package webserver.session;

import java.net.HttpCookie;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private static final Map<String, Object> sessions = new ConcurrentHashMap<>();

    public static void createSession(Object object, HttpResponse response) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, object);

        HttpCookie cookie = new HttpCookie(SESSION_COOKIE_NAME, sessionId);
        response.setCookie(cookie);
    }

    public static Object findSession(HttpRequest request) {
        HttpCookie cookie = request.getCookie(SESSION_COOKIE_NAME);
        if (cookie == null) {
            return null;
        }
        String sessionId = cookie.getValue();
        return sessions.get(sessionId);
    }

    public static void expire(HttpRequest request) {
        HttpCookie cookie = request.getCookie(SESSION_COOKIE_NAME);
        if (cookie != null) {
            sessions.remove(cookie.getValue());
        }
    }
}
