package webserver.requesthandler;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.session.SessionManager;

public class LogoutHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(LogoutHandler.class);

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        User user = (User) SessionManager.findSession(request);
        SessionManager.expire(request);

        logger.debug("{} 님이 로그아웃 하셨습니다.", user.getUserId());
        response.setRedirect(MainRequestHandler.HOME_URL);
    }
}
