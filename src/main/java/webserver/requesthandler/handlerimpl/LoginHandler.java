package webserver.requesthandler.handlerimpl;

import db.user.UserDatabase;
import db.user.UserMemoryDatabase;
import java.io.IOException;
import java.util.Objects;
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
    private final UserDatabase userDatabase = new UserMemoryDatabase();

    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        return URLConst.LOGIN_URL;
    }

    @Override
    public String handlePost(HttpRequest request, HttpResponse response) throws IOException {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        User findUser = userDatabase.findUserByLoginId(userId);
        if (findUser == null || !findUser.hasSamePassword(password)) { // 일치하는 Id가 없거나 비밀번호가 다를 경우
            return URLConst.LOGIN_URL + "/fail";
        }

        String SID = SIDMaker.makeSID();
        SessionManager.createSession(findUser, response, SID);
        String redirectURL = getRedirectURL(request);

        logger.debug("{} 님이 로그인 하셨습니다.", userId);
        return "redirect:" + redirectURL;
    }

    private String getRedirectURL(HttpRequest request) {
        String redirectURL = request.getParameter("redirectURL");
        // 널이면 홈화면으로, 요청 경로가 있다면 해당 경로로 리다이렉트
        return Objects.requireNonNullElse(redirectURL, URLConst.HOME_URL);
    }
}
