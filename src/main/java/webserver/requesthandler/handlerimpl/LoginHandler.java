package webserver.requesthandler.handlerimpl;

import db.Database;
import java.io.IOException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SIDMaker;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;
import webserver.requesthandler.session.SessionManager;

public class LoginHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);

    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        return URLConst.LOGIN_URL;
    }

    @Override
    public String handlePost(HttpRequest request, HttpResponse response) throws IOException {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        User findUser = Database.findUserById(userId);
        if (findUser == null || !findUser.hasSamePassword(password)) { // 일치하는 Id가 없거나 비밀번호가 다를 경우
            return URLConst.LOGIN_URL + "/fail";
        }

        String SID = SIDMaker.makeSID();
        SessionManager.createSession(findUser, response, SID);
        String redirectURL = request.getParameter("redirectURL");

        this.setRedirectURL(response, redirectURL);
        logger.debug("{} 님이 로그인 하셨습니다.", userId);
        return null;
    }

    private void setRedirectURL(HttpResponse response, String redirectURL) {
        if (redirectURL == null) { // 널이면 홈화면으로, 요청 경로가 있다면 해당 경로로 리다이렉트
            response.setRedirect(URLConst.HOME_URL);
            return;
        }
        response.setRedirect(redirectURL);
    }
}
