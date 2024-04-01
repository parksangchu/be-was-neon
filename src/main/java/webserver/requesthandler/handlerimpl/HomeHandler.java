package webserver.requesthandler.handlerimpl;

import db.article.ArticleDatabase;
import java.io.IOException;
import model.User;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;
import webserver.requesthandler.session.SessionManager;

/**
 * 홈 화면 요청을 처리하는 핸들러입니다.
 */
public class HomeHandler implements RequestHandler {
    private final ArticleDatabase articleDatabase;

    public HomeHandler(ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    /**
     * 홈 화면을 보여주기 위한 GET 요청을 처리합니다. 최근 게시글이 있으면 그 게시글로 리다이렉트합니다.
     *
     * @param request  HTTP 요청 정보
     * @param response HTTP 응답 정보
     * @return 홈 화면 URL 또는 최근 게시글로의 리다이렉트 URL
     * @throws IOException 입출력 처리 중 발생할 수 있는 예외
     */
    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        Long recentArticleId = articleDatabase.getRecentId();
        if (recentArticleId != null) { // 아티클이 존재할 경우 최근 아티클로 이동
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
