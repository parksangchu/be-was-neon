package webserver.requesthandler.handlercommander;

import java.io.IOException;
import webserver.requesthandler.handlerimpl.RequestHandler;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

/**
 * HTTP 요청을 처리하는 핸들러를 실행하는 클래스입니다. 요청의 메소드 유형(GET, POST 등)에 따라 적절한 핸들러 메소드를 호출합니다.
 */
public class RequestHandlerCommander {
    /**
     * 주어진 RequestHandler를 사용하여 HTTP 요청을 처리하고, 처리 결과를 반환합니다.
     *
     * @param requestHandler 요청을 처리할 RequestHandler 객체.
     * @param request        HTTP 요청 객체.
     * @param response       HTTP 응답 객체.
     * @return 처리 결과를 나타내는 문자열. 뷰 이름 또는 리다이렉션 경로일 수 있습니다.
     * @throws IOException 요청 처리 중 발생하는 I/O 예외.
     */
    public static String execute(RequestHandler requestHandler, HttpRequest request, HttpResponse response)
            throws IOException {
        if (request.isGET()) {
            return requestHandler.handleGet(request, response);
        }
        if (request.isPOST()) {
            return requestHandler.handlePost(request, response);
        }
        return null;
    }
}
