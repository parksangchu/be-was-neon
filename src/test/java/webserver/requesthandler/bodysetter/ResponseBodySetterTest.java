package webserver.requesthandler.bodysetter;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;
import webserver.requesthandler.http.HttpStatus;

class ResponseBodySetterTest {
    HttpRequest request;
    HttpResponse response;

    @BeforeEach
    void setUp() {
        request = new HttpRequest();
        response = new HttpResponse();
    }

    @Test
    @DisplayName("viewPath가 null 이면 404 응답을 반환한다.")
    void setNull() throws IOException {
        ResponseBodySetter.setBody(request, response, null);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.hasEmptyBody()).isTrue();
    }

    @Test
    @DisplayName("viewPath가 /static으로 시작하면 정적 리소스를 세팅한다")
    void setStaticResource() throws IOException {
        String path = "/static/css/global.css";
        ResponseBodySetter.setBody(request, response, path);
        byte[] body = getClass().getResourceAsStream(path).readAllBytes();
        assertThat(response.getBody()).isEqualTo(body);
    }

    @Test
    @DisplayName("요청 경로와 일치하는 정적 리소스가 없다면 404응답을 반환한다.")
    void setStaticResourceFail() throws IOException {
        String path = "/static/css/globalglobal.css";
        ResponseBodySetter.setBody(request, response, path);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("viewPath가 redirect: 로 시작하면 302응답을 반환하고 Location을 설정한다.")
    void setRedirect() throws IOException {
        ResponseBodySetter.setBody(request, response, "redirect:/login");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/login");
        assertThat(response.hasEmptyBody()).isTrue();
    }

    @Test
    @DisplayName("위의 테스트의 경우가 아닌 일반적인 viewPath 경로라면 templates 폴더에 있는 html을 body에 세팅한다")
    void setHtml() throws IOException {
        String viewPath = "/login";
        ResponseBodySetter.setBody(request, response, viewPath);
        byte[] body = getClass().getResourceAsStream("/templates/login/index.html").readAllBytes();
        assertThat(response.getBody()).isEqualTo(body);
    }
}