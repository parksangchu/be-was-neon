package webserver.athenication;

import static utils.FileManager.FILE_EXTENSION_MARKER;
import static webserver.requesthandler.MainRequestHandler.CERTIFICATION_URL;
import static webserver.requesthandler.MainRequestHandler.CREATE_URL;
import static webserver.requesthandler.MainRequestHandler.HOME_URL;
import static webserver.requesthandler.MainRequestHandler.LOGIN_FORM_URL;
import static webserver.requesthandler.MainRequestHandler.REGISTRATION_URL;

import java.util.HashSet;
import java.util.Set;
import model.User;
import webserver.http.HttpRequest;
import webserver.session.SessionManager;

public class Authenticator {
    private final Set<String> unauthenticatedUrls;

    public Authenticator() {
        this.unauthenticatedUrls = initUnauthenticatedUrls();
    }

    private Set<String> initUnauthenticatedUrls() {
        Set<String> unauthenticatedUrls = new HashSet<>();
        unauthenticatedUrls.add(HOME_URL);
        unauthenticatedUrls.add(LOGIN_FORM_URL);
        unauthenticatedUrls.add(REGISTRATION_URL);
        unauthenticatedUrls.add(CREATE_URL);
        unauthenticatedUrls.add(CERTIFICATION_URL);
        return unauthenticatedUrls;
    }

    public boolean isAuthenticated(HttpRequest request) {
        String requestPath = request.getPath();
        // 정적 자원 요청인 경우 (예: css, jpg 등), 처리를 계속 진행
        if (isStaticResourceRequest(requestPath)) {
            return true;
        }

        // 로그인 여부 확인 로직
        boolean isLoggedIn = checkLoginStatus(request);

        // 로그인이 안 되어 있고 인증이 필요한 경로일 경우 로그인 페이지로 리다이렉션 설정
        if (!isLoggedIn && isLoginCheckPath(requestPath)) {
            return false;
        }
        return true;
    }

    private boolean isStaticResourceRequest(String path) {
        // 정적 자원의 확장자를 가진 요청인지 확인
        return path.contains(FILE_EXTENSION_MARKER);
    }

    private boolean checkLoginStatus(HttpRequest request) {
        // 로그인 상태 확인 로직 구현
        // 예: 쿠키 또는 세션을 통한 로그인 상태 확인
        User user = (User) SessionManager.findSession(request);
        return user != null;
    }

    private boolean isLoginCheckPath(String requestPath) {
        return !unauthenticatedUrls.contains(requestPath);
    }
}
