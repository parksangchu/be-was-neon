package webserver.requesthandler.handlerimpl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;
import webserver.requesthandler.session.SessionManager;

class HomeHandlerTest {
    RequestHandler requestHandler;
    HttpRequest request;
    HttpResponse response;

    @BeforeEach
    void setUp() {
        requestHandler = new HomeHandler();
        request = new HttpRequest();
        response = new HttpResponse();
    }

    @Test
    @DisplayName("로그인 정보가 없는 상태에서 홈화면에 접근하면 기본 화면이 나온다.")
    void accessNotLoggedInHome() throws IOException {

        Map<String, String> headers = new HashMap<>();
        request.setHeaders(headers);

        requestHandler.handleGet(request, response); // 로그인 정보가 없는 상태에서 홈화면에 접근
        byte[] body = response.getBody();
        assertThat(body).isEqualTo(getExpected("/"));
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

        requestHandler.handleGet(request, response); // 로그인 정보가 없는 상태에서 홈화면에 접근
        byte[] body = response.getBody();
        assertThat(new String(body)).contains("sangchu", "로그아웃", "글쓰기");
    }

    private byte[] getExpected(String path) throws IOException {
        return Objects.requireNonNull(getClass().getResourceAsStream("/static" + path + "/index.html")).readAllBytes();
    }
}