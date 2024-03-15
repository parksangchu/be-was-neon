package webserver.requesthandler;

import static webserver.requesthandler.MainRequestHandler.LOGIN_FORM_URL;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserMaker implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(UserMaker.class);

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        User user = new User(request.getParameter("userId"), request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));
        Database.addUser(user);
        logger.debug("신규 유저가 생성되었습니다. {}", user);
        response.setRedirect(LOGIN_FORM_URL);
    }
}
