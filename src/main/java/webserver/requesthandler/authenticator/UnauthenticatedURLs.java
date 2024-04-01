package webserver.requesthandler.authenticator;

import static webserver.requesthandler.URLConst.ARTICLE_URL;
import static webserver.requesthandler.URLConst.HOME_URL;
import static webserver.requesthandler.URLConst.LOGIN_URL;
import static webserver.requesthandler.URLConst.REGISTRATION_URL;

import java.util.HashSet;
import java.util.Set;

/**
 * 로그인이 필요하지 않은 URL 경로를 관리하는 클래스입니다.
 */
public class UnauthenticatedURLs {
    private final Set<String> store;

    /**
     * UnauthenticatedURLs 생성자. 로그인이 필요하지 않은 기본 경로를 설정합니다.
     */
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

    /**
     * 주어진 경로에 대한 로그인 검사가 필요한지 여부를 결정합니다.
     *
     * @param requestPath 검사하고자 하는 요청 경로.
     * @return 로그인 검사가 필요하지 않은 경우 true, 그렇지 않은 경우 false를 반환합니다.
     */
    public boolean isLoginCheckPath(String requestPath) {
        // 로그인이 필요한 경로인지 확인
        return store.stream()
                .noneMatch(mappedURL -> isMatched(mappedURL, requestPath));
    }


    private boolean isMatched(String mappedURL, String requestPath) {
        return requestPath.matches(mappedURL);
    }
}
