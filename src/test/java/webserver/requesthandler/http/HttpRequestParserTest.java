package webserver.requesthandler.http;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        String requestString = """
                GET /create?userId=sangchu&name=%EC%83%81%EC%B6%94&email=sangchu%40gmail.com&password=123123 HTTP/1.1
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
                Accept-Encoding: gzip, deflate, br, zstd
                Accept-Language: ko,en-US;q=0.9,en;q=0.8,ko-KR;q=0.7
                Connection: keep-alive
                Cookie: Idea-1a9f010d=715e5261-a9a9-43bf-9fc1-44f77eaa631b; Idea-510107ab=fb62cd23-96bb-4072-a6e0-96eeeafd5830; JSESSIONID=96445CD669EB1CF5B1E005FFC7104E35
                Host: localhost:63342
                Referer: http://localhost:63342/be-was-neon/src/main/resources/static/registration/index.html?_ijt=c628t5go6dji610vgfk9fu8m5p&_ij_reload=RELOAD_ON_SAVE
                Sec-Fetch-Dest: document
                Sec-Fetch-Mode: navigate
                Sec-Fetch-Site: same-origin
                Sec-Fetch-User: ?1
                Content-Type: text/html
                Content-Length: 19
                Upgrade-Insecure-Requests: 1
                User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36
                sec-ch-ua: "Chromium";v="122", "Not(A:Brand";v="24", "Google Chrome";v="122"
                sec-ch-ua-mobile: ?0
                sec-ch-ua-platform: "macOS"
                                
                source=a2&target=a4
                """;

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

        String contentType = httpRequest.getHeader("Content-Type");
        assertThat(contentType).isEqualTo("text/html");
    }

    @Test
    @DisplayName("Request Body 정보를 추출할 수 있다.")
    void getBody() {
        String body = httpRequest.getStringBody();
        assertThat(body).isEqualTo("source=a2&target=a4");
    }
}