package webserver.requesthandler.handlerimpl;

import db.article.ArticleDatabase;
import db.article.ArticleMemoryDatabase;
import java.io.IOException;
import model.User;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;
import webserver.requesthandler.session.SessionManager;

public class HomeHandler implements RequestHandler {
    private final ArticleDatabase articleDatabase = new ArticleMemoryDatabase();

    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        Long recentArticleId = articleDatabase.getRecentId();
        if (recentArticleId != 0) { // 아티클이 존재할 경우 최근 아티클로 이동
            return "redirect:" + URLConst.ARTICLE_URL + "?aid=" + recentArticleId;
        }
        //존재하지 않을 경우 아티클이 존재하지 않다는 화면 표시
        User user = (User) SessionManager.findSession(request);
        if (user == null) {
            return URLConst.NO_ARTICLE_URL;
        }
        return URLConst.NO_ARTICLE_URL + URLConst.LOGGED_IN;
    }

    @Override
    public String handlePost(HttpRequest request, HttpResponse response) {
        return null;
    }
}
