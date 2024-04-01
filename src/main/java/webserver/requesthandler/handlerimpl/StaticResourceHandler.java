package webserver.requesthandler.handlerimpl;

import java.io.IOException;
import utils.FileManager;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

/**
 * 정적 리소스 요청을 처리하는 핸들러입니다.
 */
public class StaticResourceHandler implements RequestHandler {
    /**
     * 정적 리소스를 가져오기 위한 GET 요청을 처리합니다.
     *
     * @param request  HTTP 요청 정보
     * @param response HTTP 응답 정보
     * @return 요청된 정적 리소스의 경로
     * @throws IOException 입출력 처리 중 발생할 수 있는 예외
     */
    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        String requestURL = request.getURL();
        if (!FileManager.isFile(requestURL)) {
            return null;
        }
        if (FileManager.getStaticResource(requestURL) == null) {
            return null;
        }
        return requestURL;
    }

    @Override
    public String handlePost(HttpRequest request, HttpResponse response) throws IOException {
        return null;
    }
}
