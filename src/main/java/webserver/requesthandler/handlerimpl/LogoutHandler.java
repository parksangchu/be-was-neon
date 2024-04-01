package webserver.requesthandler.handlerimpl;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;
import webserver.requesthandler.session.SessionManager;

/**
 * 로그아웃 요청을 처리하는 핸들러입니다.
 */
public class LogoutHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(LogoutHandler.class);

    /**
     * 사용자의 로그아웃 요청을 처리하기 위한 GET 메소드입니다. 로그아웃 후 홈 화면으로 리다이렉트합니다.
     *
     * @param request  HTTP 요청 정보
     * @param response HTTP 응답 정보
     * @return 홈 화면으로의 리다이렉트 URL
     */
    @Override
    public String handleGet(HttpRequest request, HttpResponse response) {
        User user = (User) SessionManager.findSession(request);

        if (user != null) {
            SessionManager.expire(request);
            logger.debug("{} 님이 로그아웃 하셨습니다.", user.getLoginId());
            return "redirect:" + URLConst.HOME_URL;
        }
        return null;
    }

    @Override
    public String handlePost(HttpRequest request, HttpResponse response) {
        return null;
    }
}
