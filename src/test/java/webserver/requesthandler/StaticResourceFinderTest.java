package webserver.requesthandler;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

class StaticResourceFinderTest {
    StaticResourceFinder staticResourceFinder;

    HttpRequest request;
    HttpResponse response;

    @BeforeEach
    void setUp() {
        staticResourceFinder = new StaticResourceFinder();
        request = new HttpRequest();
        response = new HttpResponse();
    }

    @Test
    @DisplayName("요청된 경로와 일치하는 정적 리소스가 있으면 해당 리소스를 읽고 저장한다..")
    void findStaticResource() throws IOException {
        String requestPath = "/registration";
        request.setPath(requestPath);
        staticResourceFinder.handle(request, response);
        HttpStatus status = response.getStatus();
        byte[] body = response.getBody();
        assertThat(status).isEqualTo(HttpStatus.OK);
        assertThat(body).isEqualTo(
                Objects.requireNonNull(getClass().getResourceAsStream("/static" + requestPath + "/index.html"))
                        .readAllBytes());
    }

    @Test
    @DisplayName("요청된 경로와 일치하는 정적 리소스가 없으면 404 응답을 반환한다.")
    void findStaticInvalidResource() throws IOException {
        request.setPath("/abc");
        staticResourceFinder.handle(request, response);
        HttpStatus status = response.getStatus();
        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND);
    }

}