package webserver.requesthandler.handlerimpl;

import db.user.UserDatabase;
import java.io.IOException;
import java.util.List;
import model.User;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

public class UserListHandler implements RequestHandler {
    private final UserDatabase userDatabase;

    public UserListHandler(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        List<User> userList = userDatabase.findAll();
        request.setAttribute("userList", userList);
        return URLConst.USER_LIST_URL;
    }

    @Override
    public String handlePost(HttpRequest request, HttpResponse response) throws IOException {
        return null;
    }
}
