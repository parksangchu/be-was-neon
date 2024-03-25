package webserver.requesthandler.handlerimpl;

import db.Database;
import java.io.IOException;
import java.util.List;
import model.User;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

public class UserListHandler implements RequestHandler {

    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        List<User> userList = Database.findAll();
        request.setAttribute("userList", userList);
        return URLConst.USER_LIST_URL;
    }

    @Override
    public String handlePost(HttpRequest request, HttpResponse response) throws IOException {
        response.setNotFound();
        return null;
    }
}
