package webserver.requesthandler.handlerimpl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

class StaticResourceHandlerTest {
    StaticResourceHandler staticResourceHandler;
    HttpRequest request;
    HttpResponse response;

    @BeforeEach
    void setUp() {
        staticResourceHandler = new StaticResourceHandler();
        request = new HttpRequest();
        response = new HttpResponse();
    }

    @Test
    @DisplayName("요청된 경로와 일치하는 정적 리소스가 있으면 해당 리소스의 경로를 반환한다.")
    void findStaticResource() throws IOException {
        String requestPath = "/img/like.svg";
        request.setURL(requestPath);
        String viewPath = staticResourceHandler.handleGet(request, response);
        assertThat(viewPath).isEqualTo(requestPath);
    }

    @ParameterizedTest
    @DisplayName("요청된 경로와 일치하는 정적 리소스가 없으면 null을 반환한다.")
    @ValueSource(strings = {"/abc", "/abc.css"})
    void findStaticInvalidResource(String path) throws IOException {
        request.setURL(path);
        String result = staticResourceHandler.handleGet(request, response);
        assertThat(result).isNull();
    }
}