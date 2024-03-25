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
