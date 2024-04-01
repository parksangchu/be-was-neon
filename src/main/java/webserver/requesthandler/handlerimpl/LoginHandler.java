package webserver.requesthandler.handlerimpl;

import db.user.UserDatabase;
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

/**
 * 로그인 요청을 처리하는 핸들러입니다.
 */
public class LoginHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);
    private final UserDatabase userDatabase;

    public LoginHandler(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    /**
     * 로그인 폼을 보여주기 위한 GET 요청을 처리합니다.
     *
     * @param request  HTTP 요청 정보
     * @param response HTTP 응답 정보
     * @return 로그인 폼 URL
     * @throws IOException 입출력 처리 중 발생할 수 있는 예외
     */
    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        return URLConst.LOGIN_URL;
    }

    /**
     * 사용자 로그인 정보를 처리하기 위한 POST 요청을 처리합니다. 성공적으로 로그인한 경우, 지정된 리다이렉션 URL로 이동합니다.
     *
     * @param request  HTTP 요청 정보
     * @param response HTTP 응답 정보
     * @return 로그인 성공 후 리다이렉트될 URL
     * @throws IOException 입출력 처리 중 발생할 수 있는 예외
     */
    @Override
    public String handlePost(HttpRequest request, HttpResponse response) throws IOException {
        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");

        User findUser = userDatabase.findUserByLoginId(loginId);
        if (findUser == null || !findUser.hasSamePassword(password)) { // 일치하는 Id가 없거나 비밀번호가 다를 경우
            return URLConst.LOGIN_URL + "/fail";
        }

        String SID = SIDMaker.makeSID();
        SessionManager.createSession(findUser, response, SID);
        String redirectURL = getRedirectURL(request);

        logger.debug("{} 님이 로그인 하셨습니다.", loginId);
        return "redirect:" + redirectURL;
    }

    private String getRedirectURL(HttpRequest request) {
        String redirectURL = request.getParameter("redirectURL");
        // 널이면 홈화면으로, 요청 경로가 있다면 해당 경로로 리다이렉트
        return Objects.requireNonNullElse(redirectURL, URLConst.HOME_URL);
    }
}
