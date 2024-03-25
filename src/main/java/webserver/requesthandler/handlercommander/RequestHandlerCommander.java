package webserver.requesthandler.handlercommander;

import java.io.IOException;
import webserver.requesthandler.handlerimpl.RequestHandler;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

public class RequestHandlerCommander {
    public static String execute(RequestHandler requestHandler, HttpRequest request, HttpResponse response)
            throws IOException {
        if (request.isGET()) {
            return requestHandler.handleGet(request, response);
        }
        if (request.isPOST()) {
            return requestHandler.handlePost(request, response);
        }
        throw new IllegalArgumentException("잘못된 접근입니다.");
    }
}
