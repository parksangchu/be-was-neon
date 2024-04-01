package webserver.requesthandler.bodysetter;

import java.io.IOException;
import utils.FileManager;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

/**
 * HTTP 응답의 본문을 설정하는 유틸리티 클래스입니다.
 */
public class ResponseBodySetter {
    /**
     * 요청에 따라 적절한 응답 본문을 설정합니다. 정적 리소스, HTML 뷰, 또는 리다이렉션을 처리할 수 있습니다.
     *
     * @param request  요청 객체.
     * @param response 응답 객체.
     * @param viewPath 처리할 뷰 또는 리소스의 경로.
     * @throws IOException 파일 I/O 작업 중 발생하는 예외.
     */
    public static void setBody(HttpRequest request, HttpResponse response, String viewPath)
            throws IOException {
        if (viewPath == null) {
            response.setNotFound();
            byte[] body = FileManager.getTemplate("/error/404");
            response.setHtmlBody(body);
            return;
        }
        if (FileManager.isFile(viewPath)) {
            StaticResourceSetter.setStaticResource(response, viewPath);
            return;
        }
        if (viewPath.startsWith("redirect:")) {
            String redirectPath = viewPath.split(":")[1];
            response.setRedirect(redirectPath);
            return;
        }
        HtmlSetter.setView(request, response, viewPath);
    }
}
