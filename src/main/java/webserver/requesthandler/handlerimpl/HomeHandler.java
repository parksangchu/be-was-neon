package webserver.requesthandler.handlerimpl;

import java.io.IOException;
import model.User;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;
import webserver.requesthandler.session.SessionManager;

public class HomeHandler implements RequestHandler {

    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        User user = (User) SessionManager.findSession(request);
        if (user == null) { // 로그인 안된 사용자
            return URLConst.HOME_URL;
        }
        request.setAttribute("userId", user.getUserId());
        return URLConst.LOGGED_IN_USER_HOME_URL;
    }

    @Override
    public String handlePost(HttpRequest request, HttpResponse response) {
        response.setNotFound();
        return null;
    }

}
