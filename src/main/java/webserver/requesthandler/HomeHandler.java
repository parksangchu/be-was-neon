package webserver.requesthandler;

import static webserver.requesthandler.StaticResourceFinder.DEFAULT_FILE;
import static webserver.requesthandler.StaticResourceFinder.STATIC_DIRECTORY;

import db.Database;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.Objects;
import model.User;
import webserver.http.ContentType;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class HomeHandler implements RequestHandler {
    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException {
        HttpCookie cookie = request.getCookie("userId");
        if (cookie == null) {
            setDefaultHome(response);
            return;
        }
        String userId = cookie.getValue();
        User user = Database.findUserById(userId);
        if (user == null) {
            setDefaultHome(response);
            return;
        }
        response.setRedirect("/main");
    }

    private void setDefaultHome(HttpResponse response) throws IOException {
        byte[] body = Objects.requireNonNull(getClass().getResourceAsStream(STATIC_DIRECTORY + DEFAULT_FILE))
                .readAllBytes();
        response.setBody(body, ContentType.HTML);
    }
}
