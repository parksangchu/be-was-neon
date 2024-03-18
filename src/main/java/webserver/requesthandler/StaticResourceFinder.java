package webserver.requesthandler;

import java.io.IOException;
import java.io.InputStream;
import utils.FileManager;
import webserver.http.ContentType;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

public class StaticResourceFinder implements RequestHandler {
    public static final String STATIC_DIRECTORY = "/static";

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException {
        String requestPath = request.getPath();
        InputStream resourceStream = getStaticResourceStream(requestPath);

        if (resourceStream == null) {
            response.setStatus(HttpStatus.NOT_FOUND); // 파일을 찾을 수 없는 경우 404 응답
            return;
        }

        String fileExtension = FileManager.getFileExtension(requestPath);
        byte[] body = resourceStream.readAllBytes();
        ContentType contentType = ContentType.getContentTypeByExtension(fileExtension);
        response.setBody(body, contentType);
    }


    private InputStream getStaticResourceStream(String path) {
        String staticPath = getStaticPath(path);
        return getClass().getResourceAsStream(staticPath);
    }

    private String getStaticPath(String path) {
        return STATIC_DIRECTORY + path;
    }

}
