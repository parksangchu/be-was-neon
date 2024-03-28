package webserver.requesthandler.handlerimpl;

import db.article.ArticleDatabase;
import db.article.ArticleMemoryDatabase;
import java.io.IOException;
import model.Article;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;
import webserver.requesthandler.session.SessionManager;

public class ArticleFormHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(ArticleFormHandler.class);
    private final ArticleDatabase articleDatabase = new ArticleMemoryDatabase();

    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        return URLConst.ARTICLE_FORM_URL;
    }

    @Override
    public String handlePost(HttpRequest request, HttpResponse response) throws IOException {
        User user = (User) SessionManager.findSession(request);
        if (user != null) {
            String content = new String(request.getBody());
            byte[] file = request.getFile();
            Article article = new Article(user.getUserId(), content, file); // 아티클을 생성하고 데이터베이스에 저장
            articleDatabase.addArticle(article);
            logger.debug("새로운 게시글이 작성되었습니다. aid={}, userId={}", article.getSequenceId(), user.getUserId());
        }

        return "redirect:" + URLConst.HOME_URL;
    }
}
