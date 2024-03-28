package webserver.requesthandler.handlerimpl;

import static org.assertj.core.api.Assertions.assertThat;

import db.article.ArticleDatabase;
import db.article.ArticleMemoryDatabase;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import model.Article;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;
import webserver.requesthandler.session.SessionManager;

class HomeHandlerTest {
    ArticleDatabase articleDatabase = new ArticleMemoryDatabase();
    RequestHandler requestHandler;
    HttpRequest request;
    HttpResponse response;

    @BeforeEach
    void setUp() {
        articleDatabase.clear();
        requestHandler = new HomeHandler();
        request = new HttpRequest();
        response = new HttpResponse();
    }

    @Test
    @DisplayName("로그인 정보가 없는 상태에서 홈화면에 접근하면 기본 화면이 나온다.")
    void accessNotLoggedInHome() throws IOException {

        Map<String, String> headers = new HashMap<>();
        request.setHeaders(headers);

        String viewPath = requestHandler.handleGet(request, response);// 로그인 정보가 없는 상태에서 홈화면에 접근
        assertThat(viewPath).isEqualTo("/noarticle");
    }

    @Test
    @DisplayName("로그인 정보가 있는 상태에서 홈화면에 접근하면 로그인 상태의 화면이 나온다.")
    void accessLoggedInHome() throws IOException {
        SessionManager.createSession(
                new User("sangchu", "123", "상추", "sangchu@gmail.com")
                , response, "123456");
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "SID=123456"); // 쿠키에 시드정보 입력

        request.setHeaders(headers);

        String viewPath = requestHandler.handleGet(request, response);// 로그인 정보가 없는 상태에서 홈화면에 접근
        assertThat(viewPath).isEqualTo("/noarticle/loggedin");
    }

    @Test
    @DisplayName("아티클이 존재하는 상황에서 홈화면에 접근하면 최신 아티클로 이동한다.")
    void accessHomeWithArticle() throws IOException {
        Map<String, String> headers = new HashMap<>();
        request.setHeaders(headers);

        articleDatabase.addArticle(new Article("sangchu", "상추입니다.", new byte[0]));
        articleDatabase.addArticle(new Article("sangchu", "상추입니다.", new byte[0]));

        String viewPath = requestHandler.handleGet(request, response);
        assertThat("redirect:/article?aid=2").isEqualTo(viewPath);
    }
}