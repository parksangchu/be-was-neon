package webserver.requesthandler.handlerimpl;

import java.io.IOException;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

public interface RequestHandler {

    String handleGet(HttpRequest request, HttpResponse response) throws IOException;

    String handlePost(HttpRequest request, HttpResponse response) throws IOException;
}
