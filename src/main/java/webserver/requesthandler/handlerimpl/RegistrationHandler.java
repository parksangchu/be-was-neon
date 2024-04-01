package webserver.requesthandler.handlerimpl;

import db.user.UserDatabase;
import java.io.IOException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

/**
 * 사용자 등록(회원가입) 요청을 처리하는 핸들러입니다.
 */
public class RegistrationHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationHandler.class);
    private final UserDatabase userDatabase;

    public RegistrationHandler(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    /**
     * 회원가입 폼을 보여주기 위한 GET 요청을 처리합니다.
     *
     * @param request  HTTP 요청 정보
     * @param response HTTP 응답 정보
     * @return 회원가입 폼 URL
     * @throws IOException 입출력 처리 중 발생할 수 있는 예외
     */
    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        return URLConst.REGISTRATION_URL;
    }

    /**
     * 사용자의 회원가입 정보를 처리하기 위한 POST 요청을 처리합니다. 회원가입 성공 후 로그인 페이지로 리다이렉트합니다.
     *
     * @param request  HTTP 요청 정보
     * @param response HTTP 응답 정보
     * @return 로그인 페이지로의 리다이렉트 URL
     */
    @Override
    public String handlePost(HttpRequest request, HttpResponse response) {
        User user = new User(request.getParameter("loginId"), request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));
        userDatabase.addUser(user);
        logger.debug("신규 유저가 생성되었습니다. {}", user);
        return "redirect:" + URLConst.LOGIN_URL;
    }
}
