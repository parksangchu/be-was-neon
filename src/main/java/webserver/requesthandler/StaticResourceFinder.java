package webserver.requesthandler;

import static utils.FileManager.FILE_EXTENSION_MARKER;

import java.io.IOException;
import java.io.InputStream;
import utils.FileManager;
import webserver.http.ContentType;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

public class StaticResourceFinder implements RequestHandler {
    public static final String DEFAULT_FILE = "/index.html";
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
        String modifiedPath = getModifiedPath(path);
        return getClass().getResourceAsStream(modifiedPath);
    }

    private String getModifiedPath(String path) {
        if (path.contains(FILE_EXTENSION_MARKER)) {
            return STATIC_DIRECTORY + path;
        }
        return STATIC_DIRECTORY + path + DEFAULT_FILE;
    }

}
