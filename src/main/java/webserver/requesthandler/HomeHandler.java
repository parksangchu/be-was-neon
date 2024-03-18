package webserver.requesthandler;

import java.io.IOException;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.session.SessionManager;

public class HomeHandler implements RequestHandler {

    @Override
    public void handleGet(HttpRequest request, HttpResponse response) throws IOException {
        User user = (User) SessionManager.findSession(request);
        if (user == null) { // 로그인 안된 사용자
            setHTMLToBody(response, URLConst.HOME_URL);
            return;
        }
        setHTMLToBody(response, URLConst.LOGGED_IN_USER_HOME_URL);
    }

    @Override
    public void handlePost(HttpRequest request, HttpResponse response) {
        response.setNotFound();
    }
}
