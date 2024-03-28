package webserver.requesthandler.http;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static webserver.requesthandler.http.HttpConst.CRLF;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.requesthandler.httpParser.HttpRequestParser;

class HttpRequestParserTest {

    HttpRequest httpRequest;

    @BeforeEach
    void setUp() throws IOException {
        String requestString =
                "GET /create?loginId=sangchu&name=%EC%83%81%EC%B6%94&email=sangchu%40gmail.com&password=123123 HTTP/1.1"
                        + CRLF
                        + "Content-Type: application/x-www-form-urlencoded"
                        + CRLF
                        + "Content-Length: 19"
                        + CRLF
                        + CRLF
                        + "source=a2&target=a4";

        byte[] byteArray = requestString.getBytes(StandardCharsets.UTF_8);

        InputStream in = new ByteArrayInputStream(byteArray);
        httpRequest = HttpRequestParser.parse(in);
    }

    @Test
    @DisplayName("Request Line 정보를 추출할 수 있다.")
    void getLine() {
        assertThat(httpRequest.getMethod()).isEqualTo("GET");
        assertThat(httpRequest.getURL()).isEqualTo("/create");
    }

    @Test
    @DisplayName("헤더 정보를 추출할 수 있다.")
    void getHeaders() {
        String contentType = httpRequest.getContentType();
        assertThat(contentType).isEqualTo(HttpConst.HTML_FORM_DATA);
    }

    @Test
    @DisplayName("Request Body 정보를 추출할 수 있다.")
    void getBody() {
        String body = httpRequest.getStringBody();
        assertThat(body).isEqualTo("source=a2&target=a4");
    }
}