package webserver.requesthandler.session;

import java.net.HttpCookie;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

/**
 * 사용자 세션을 관리하는 클래스입니다.
 */
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "SID";
    public static final int EXPIRATION_PERIOD = 1800;
    private static final Map<String, Object> sessions = new ConcurrentHashMap<>();

    /**
     * 새로운 세션을 생성하고 클라이언트에 쿠키로 세션 ID를 설정합니다.
     *
     * @param object   세션에 저장할 객체
     * @param response 클라이언트에 응답을 보낼 HttpResponse 객체
     * @param SID      생성할 세션 ID
     */
    public static void createSession(Object object, HttpResponse response, String SID) {
        sessions.put(SID, object);
        HttpCookie cookie = new HttpCookie(SESSION_COOKIE_NAME, SID);
        cookie.setMaxAge(EXPIRATION_PERIOD); // 만료 시간 30분
        response.addCookie(cookie);
    }

    /**
     * 주어진 HttpRequest에 해당하는 세션을 찾습니다.
     *
     * @param request 클라이언트로부터 받은 HttpRequest
     * @return 찾은 세션 객체, 없으면 null 반환
     */
    public static Object findSession(HttpRequest request) {
        HttpCookie cookie = request.getCookie(SESSION_COOKIE_NAME);
        if (cookie == null) {
            return null;
        }
        String sessionId = cookie.getValue();
        return sessions.get(sessionId);
    }

    /**
     * 주어진 HttpRequest에 해당하는 세션을 만료시킵니다.
     *
     * @param request 클라이언트로부터 받은 HttpRequest
     */
    public static void expire(HttpRequest request) {
        HttpCookie cookie = request.getCookie(SESSION_COOKIE_NAME);
        if (cookie != null) {
            sessions.remove(cookie.getValue());
        }
    }
}
