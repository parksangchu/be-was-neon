package webserver.requesthandler.handlerimpl;

import db.article.ArticleDatabase;
import java.io.IOException;
import java.util.Base64;
import model.Article;
import model.User;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;
import webserver.requesthandler.session.SessionManager;

/**
 * 개별 게시글 조회 요청을 처리하는 핸들러입니다.
 */
public class ArticleHandler implements RequestHandler {
    public static final String BASE_64_IMG_PREFIX = "data:image/jpeg;base64,";
    private final ArticleDatabase articleDatabase;

    public ArticleHandler(ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    /**
     * 개별 게시글의 내용을 보여주기 위한 GET 요청을 처리합니다.
     *
     * @param request  HTTP 요청 정보
     * @param response HTTP 응답 정보
     * @return 게시글 페이지의 URL 또는 로그인한 사용자를 위한 URL
     * @throws IOException 입출력 처리 중 발생할 수 있는 예외
     */
    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        String aidString = request.getParameter("aid");
        if (aidString == null) { // aid를 포함한 쿼리 요청이 없을시 홈으로 리다이렉트
            return "redirect:" + URLConst.HOME_URL;
        }
        long aid = Long.parseLong(aidString);
        Article article = articleDatabase.findArticleBySequenceId(aid);

        if (article == null) { // aid와 일치하는 아티클이 없으면 홈으로 리다이렉트
            return "redirect:" + URLConst.HOME_URL;
        }
        request.setAttribute("article.userId", article.getUserId()); // 아티클을 작성한 유저ID
        request.setAttribute("article.content", article.getContent()); // 아티클의 내용
        request.setAttribute("article.img", getEncodedImg(article)); // 아티클의 이미지
        request.setAttribute("article.prev", getPrevAid(aid)); // 이전 아티클 번호
        request.setAttribute("article.next", getNextAid(aid)); // 다음 아티클 번호

        User user = (User) SessionManager.findSession(request);
        if (user == null) { // 로그인 여부에 따라 다른 화면 렌더링
            return URLConst.ARTICLE_URL;
        }
        return URLConst.ARTICLE_URL + URLConst.LOGGED_IN;
    }

    @Override
    public String handlePost(HttpRequest request, HttpResponse response) throws IOException {
        return null;
    }

    private long getPrevAid(long aid) {
        if (aid == articleDatabase.getFirstId()) { // 첫번째 글이라면 마지막 aid 반환
            return articleDatabase.getRecentId();
        }
        return aid - 1;
    }

    private long getNextAid(long aid) {
        if (aid == articleDatabase.getRecentId()) {
            return articleDatabase.getFirstId(); // 마지막 글이라면 첫번째 aid를 반환
        }
        return aid + 1; // 아니라면 다음 aid를 반환
    }

    private String getEncodedImg(Article article) {
        return BASE_64_IMG_PREFIX + new String(Base64.getEncoder().encode(article.getFile()));
    }
}
