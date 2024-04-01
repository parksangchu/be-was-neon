package webserver.requesthandler.handlerimpl;

import java.io.IOException;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

/**
 * 댓글 관련 요청을 처리하는 핸들러입니다.
 */
public class CommentHandler implements RequestHandler {
    /**
     * 댓글 페이지를 보여주기 위한 GET 요청을 처리합니다.
     *
     * @param request  HTTP 요청 정보
     * @param response HTTP 응답 정보
     * @return 댓글 페이지의 URL
     * @throws IOException 입출력 처리 중 발생할 수 있는 예외
     */
    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        return URLConst.COMMENT_URL;
    }

    @Override
    public String handlePost(HttpRequest request, HttpResponse response) {
        return null;
    }
}
