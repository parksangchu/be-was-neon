package webserver.requesthandler.responewriter;

import static webserver.requesthandler.http.HttpConst.CRLF;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import webserver.requesthandler.http.HttpConst;
import webserver.requesthandler.http.HttpResponse;

/**
 * HttpResponse 객체를 사용하여 클라이언트에게 HTTP 응답을 보내는 클래스입니다.
 */
public class HttpResponseWriter {
    private final OutputStream out;

    /**
     * HttpResponseWriter 생성자.
     *
     * @param out 응답을 보낼 OutputStream
     */
    public HttpResponseWriter(OutputStream out) {
        this.out = out;
    }

    /**
     * 주어진 HttpResponse 객체에 따라 클라이언트에 응답을 보냅니다.
     *
     * @param response 보낼 HTTP 응답
     * @throws IOException 응답 전송 중 오류가 발생한 경우
     */
    public void send(HttpResponse response) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBytes(HttpConst.HTTP_VERSION + response.getStatus().getValue() + HttpConst.START_LINE_DELIMITER
                + response.getStatus().getReasonPhrase() + CRLF); // 스타트 라인 입력

        Map<String, String> headers = response.getHeaders(); // 헤더 입력
        for (String headerName : headers.keySet()) {
            writeHeader(headerName, headers, dos);
        }
        dos.writeBytes(CRLF);

        if (response.getBody().length > 0) { // 바디 입력
            dos.write(response.getBody());
        }
        dos.flush();
    }

    private void writeHeader(String headerName, Map<String, String> headers, DataOutputStream dos) throws IOException {
        if (isSetCookie(headerName)) {  // 쿠키는 하나씩만 Set-Cookie로 줄을 나눠 입력할 수 있으므로 따로 처리
            writeCookie(headerName, headers, dos);
            return;
        }
        dos.writeBytes(headerName + HttpConst.HEADER_DELIMITER + headers.get(headerName) + CRLF);
    }

    private boolean isSetCookie(String headerName) {
        return headerName.equals(HttpConst.HEADER_SET_COOKIE);
    }

    private void writeCookie(String headerName, Map<String, String> headers, DataOutputStream dos)
            throws IOException {
        String[] cookieValues = headers.get(headerName)
                .split(HttpConst.SET_COOKIE_DELIMITER);
        for (String cookieValue : cookieValues) {
            if (!cookieValue.isEmpty()) {
                dos.writeBytes(HttpConst.HEADER_SET_COOKIE + HttpConst.HEADER_DELIMITER + cookieValue);
                dos.writeBytes(CRLF);
            }
        }
    }
}