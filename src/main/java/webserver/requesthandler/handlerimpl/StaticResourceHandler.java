package webserver.requesthandler.handlerimpl;

import java.io.IOException;
import utils.FileManager;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

public class StaticResourceHandler implements RequestHandler {

    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        String requestURL = request.getURL();
        if (!FileManager.isFile(requestURL)) {
            response.setNotFound(); // 파일이 아닐 경우 404 응답
            return null;
        }
        return requestURL;
    }

    @Override
    public String handlePost(HttpRequest request, HttpResponse response) throws IOException {
        response.setNotFound();
        return null;
    }
}
