package webserver.athenication;

import static utils.FileManager.FILE_EXTENSION_MARKER;
import static webserver.requesthandler.MainRequestHandler.HOME_URL;

import java.util.HashSet;
import java.util.Set;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.session.SessionManager;

public class Authenticator {

    private final Set<String> unauthenticatedUrls;

    public Authenticator() {
        this.unauthenticatedUrls = initUnauthenticatedUrls();

    }

    private Set<String> initUnauthenticatedUrls() {
        Set<String> unauthenticatedUrls = new HashSet<>();
        unauthenticatedUrls.add(HOME_URL);
        unauthenticatedUrls.add("/login");
        unauthenticatedUrls.add("/registration");
        unauthenticatedUrls.add("/create");
        unauthenticatedUrls.add("/certification");
        return unauthenticatedUrls;
    }

    public void authenticate(HttpRequest request, HttpResponse response) {
        String requestPath = request.getPath();
        // 정적 자원 요청인 경우 (예: css, jpg 등), 처리를 계속 진행
        if (isStaticResourceRequest(requestPath)) {
            return;
        }

        // 로그인 여부 확인 로직
        boolean isLoggedIn = checkLoginStatus(request);

        // 로그인이 안 되어 있고 인증이 필요한 경로일 경우 홈으로 리다이렉션
        if (!isLoggedIn && !unauthenticatedUrls.contains(requestPath)) {
            response.setRedirect(HOME_URL); // 로그인이 안 되어 있다면 추가 처리를 중단하고 리다이렉션
        }
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
}
