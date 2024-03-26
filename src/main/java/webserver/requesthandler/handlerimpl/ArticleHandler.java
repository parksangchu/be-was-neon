package webserver.requesthandler.handlerimpl;

import db.article.ArticleDatabase;
import db.article.ArticleMemoryDatabase;
import java.io.IOException;
import model.Article;
import model.User;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;
import webserver.requesthandler.session.SessionManager;

public class ArticleHandler implements RequestHandler {
    private final ArticleDatabase articleDatabase = new ArticleMemoryDatabase();

    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        return URLConst.ARTICLE_URL;
    }

    @Override
    public String handlePost(HttpRequest request, HttpResponse response) throws IOException {
        User user = (User) SessionManager.findSession(request);
        if (user != null) {
            String content = new String(request.getBody());
            byte[] file = request.getFile();
            Article article = new Article(user.getUserId(), content, file); // 아티클을 생성하고 데이터베이스에 저장
            articleDatabase.addArticle(article);
        }

        return "redirect:" + URLConst.HOME_URL;
    }
}
