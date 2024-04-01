package webserver.requesthandler.bodysetter;

import java.io.IOException;
import utils.FileManager;
import webserver.requesthandler.http.ContentType;
import webserver.requesthandler.http.HttpResponse;

/**
 * 정적 리소스를 처리하여 HTTP 응답 본문에 설정하는 클래스입니다.
 */
public class StaticResourceSetter {
    /**
     * 지정된 경로의 정적 리소스 파일을 로드하여 HTTP 응답 본문에 설정합니다.
     *
     * @param response 응답 객체.
     * @param viewPath 리소스 파일의 경로.
     * @throws IOException 파일을 로드하는 과정에서 발생하는 예외.
     */
    public static void setStaticResource(HttpResponse response, String viewPath) throws IOException {
        byte[] staticResource = FileManager.getStaticResource(viewPath);
        String fileExtension = FileManager.getFileExtension(viewPath);
        response.setBody(staticResource, ContentType.getContentTypeByExtension(fileExtension));
    }
}
