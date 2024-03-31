package webserver.requesthandler.handlerimpl;

import db.user.UserDatabase;
import java.io.IOException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

public class RegistrationHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationHandler.class);
    private final UserDatabase userDatabase;

    public RegistrationHandler(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        return URLConst.REGISTRATION_URL;
    }

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
