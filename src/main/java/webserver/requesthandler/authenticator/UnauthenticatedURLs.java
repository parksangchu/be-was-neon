package webserver.requesthandler.authenticator;

import static webserver.requesthandler.URLConst.ARTICLE_URL;
import static webserver.requesthandler.URLConst.HOME_URL;
import static webserver.requesthandler.URLConst.LOGIN_URL;
import static webserver.requesthandler.URLConst.REGISTRATION_URL;

import java.util.HashSet;
import java.util.Set;

public class UnauthenticatedURLs {
    private final Set<String> store;

    public UnauthenticatedURLs() {
        store = new HashSet<>();
        store.add(HOME_URL);
        store.add(ARTICLE_URL);
        store.add(LOGIN_URL);
        store.add(REGISTRATION_URL);
        store.add("/css/.*");
        store.add("/img/.*");
        store.add("/favicon.ico");
    }

    public boolean isLoginCheckPath(String requestPath) {
        // 로그인이 필요한 경로인지 확인
        return store.stream()
                .noneMatch(mappedURL -> isMatched(mappedURL, requestPath));
    }

    private boolean isMatched(String mappedURL, String requestPath) {
        return requestPath.matches(mappedURL);
    }
}
