package webserver.requesthandler.authenticator;

import java.io.IOException;
import model.User;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;
import webserver.requesthandler.session.SessionManager;

/**
 * HTTP 요청의 인증 상태를 관리하는 클래스입니다. 특정 URL 경로에 대한 접근을 로그인 여부에 따라 제한합니다.
 */
public class Authenticator {

    private final UnauthenticatedURLs unauthenticatedURLs; // 로그인이 필요 없는 경로 모음

    /**
     * Authenticator 생성자.
     *
     * @param unAuthenticatedURLs 로그인이 필요 없는 URL 경로를 관리하는 객체.
     */

    public Authenticator(UnauthenticatedURLs unAuthenticatedURLs) {
        this.unauthenticatedURLs = unAuthenticatedURLs;
    }

    /**
     * 요청이 인증(로그인)되었는지 확인합니다.
     *
     * @param request  요청에 대한 HTTP 정보를 담고 있는 객체.
     * @param response 응답에 대한 HTTP 정보를 설정할 객체.
     * @return 인증된 경우 true, 그렇지 않은 경우 false를 반환합니다.
     * @throws IOException I/O 예외가 발생한 경우.
     */

    public boolean isAuthenticated(HttpRequest request, HttpResponse response) throws IOException {
        String requestPath = request.getURL();
        if (!unauthenticatedURLs.isLoginCheckPath(requestPath)) { // 로그인이 필요하지 않은 경로면 검사 X
            return true;
        }

        // 로그인이 필요할때 로직
        boolean isLoggedIn = isLoggedIn(request);
        if (isLoggedIn) { // 로그인이 되어 있으면 true 리턴
            return true;
        }

        response.setRedirect("/login?redirectURL=" + requestPath); // 로그인이 되어있지 않으면 리다이렉트 설정
        return false;
    }
    
    private boolean isLoggedIn(HttpRequest request) {
        // 로그인 상태 확인
        User user = (User) SessionManager.findSession(request);
        return user != null;
    }
}
