package webserver.requesthandler.handlerimpl;

import java.io.IOException;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

public class CommentHandler implements RequestHandler {
    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        return URLConst.COMMENT_URL;
    }

    @Override
    public String handlePost(HttpRequest request, HttpResponse response) {
        response.setNotFound();
        return null;
    }
}
