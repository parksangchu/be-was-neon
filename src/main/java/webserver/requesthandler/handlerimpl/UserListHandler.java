package webserver.requesthandler.handlerimpl;

import db.user.UserDatabase;
import java.io.IOException;
import java.util.List;
import model.User;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

/**
 * 사용자 목록 페이지 요청을 처리하는 핸들러입니다.
 */
public class UserListHandler implements RequestHandler {
    private final UserDatabase userDatabase;

    public UserListHandler(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    /**
     * 사용자 목록을 보여주기 위한 GET 요청을 처리합니다.
     *
     * @param request  HTTP 요청 정보
     * @param response HTTP 응답 정보
     * @return 사용자 목록 페이지 URL
     * @throws IOException 입출력 처리 중 발생할 수 있는 예외
     */
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
