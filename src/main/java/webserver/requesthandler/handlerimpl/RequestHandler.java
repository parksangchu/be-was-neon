package webserver.requesthandler.handlerimpl;

import java.io.IOException;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

public interface RequestHandler {
    default String handle(HttpRequest request, HttpResponse response) throws IOException {
        if (request.isGET()) {
            return handleGet(request, response);
        }
        if (request.isPOST()) {
            return handlePost(request, response);
        }
        response.setNotFound();
        return null;
    }

    String handleGet(HttpRequest request, HttpResponse response) throws IOException;

    String handlePost(HttpRequest request, HttpResponse response) throws IOException;
}
