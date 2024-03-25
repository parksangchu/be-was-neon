package webserver.requesthandler.authenticator;

import static webserver.requesthandler.URLConst.HOME_URL;
import static webserver.requesthandler.URLConst.LOGIN_URL;
import static webserver.requesthandler.URLConst.REGISTRATION_URL;
import static webserver.requesthandler.URLConst.STATIC_RESOURCE_URL;

import java.util.HashSet;
import java.util.Set;

public class UnauthenticatedURLs {
    private final Set<String> store;

    public UnauthenticatedURLs() {
        store = new HashSet<>();
        store.add(HOME_URL);
        store.add(LOGIN_URL);
        store.add(REGISTRATION_URL);
        store.add(STATIC_RESOURCE_URL);
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
