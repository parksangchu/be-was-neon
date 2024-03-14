package webserver.requesthandler;

import java.io.IOException;
import java.net.HttpCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class LogoutHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(LogoutHandler.class);

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException {
        HttpCookie cookie = new HttpCookie("userId", null);
        cookie.setMaxAge(0);
        response.setCookie(cookie);
        response.setRedirect(MainRequestHandler.HOME_URL);
    }
}
