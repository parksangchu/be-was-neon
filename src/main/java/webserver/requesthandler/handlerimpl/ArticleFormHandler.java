package webserver.requesthandler.handlerimpl;

import db.article.ArticleDatabase;
import java.io.IOException;
import model.Article;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;
import webserver.requesthandler.session.SessionManager;

/**
 * 게시글 작성 폼 요청을 처리하는 핸들러입니다.
 */
public class ArticleFormHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(ArticleFormHandler.class);
    private final ArticleDatabase articleDatabase;

    public ArticleFormHandler(ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    /**
     * 게시글 작성 폼을 보여주기 위한 GET 요청을 처리합니다.
     *
     * @param request  HTTP 요청 정보
     * @param response HTTP 응답 정보
     * @return 게시글 작성 폼의 URL
     * @throws IOException 입출력 처리 중 발생할 수 있는 예외
     */
    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        return URLConst.ARTICLE_FORM_URL;
    }

    /**
     * 사용자가 작성한 게시글을 저장하기 위한 POST 요청을 처리합니다.
     *
     * @param request  HTTP 요청 정보
     * @param response HTTP 응답 정보
     * @return 홈 화면으로의 리다이렉트 URL
     * @throws IOException 입출력 처리 중 발생할 수 있는 예외
     */
    @Override
    public String handlePost(HttpRequest request, HttpResponse response) throws IOException {
        User user = (User) SessionManager.findSession(request);
        if (user != null) {
            String content = new String(request.getBody());
            byte[] file = request.getFile();
            Article article = new Article(user.getLoginId(), content, file); // 아티클을 생성하고 데이터베이스에 저장
            articleDatabase.addArticle(article);
            logger.debug("새로운 게시글이 작성되었습니다. aid={}, userId={}", article.getSequenceId(), user.getLoginId());
        }

        return "redirect:" + URLConst.HOME_URL;
    }
}
