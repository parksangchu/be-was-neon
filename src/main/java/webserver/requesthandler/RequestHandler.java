package webserver.requesthandler;

import java.io.IOException;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface RequestHandler {
    void handle(HttpRequest request, HttpResponse response) throws IOException;
}
