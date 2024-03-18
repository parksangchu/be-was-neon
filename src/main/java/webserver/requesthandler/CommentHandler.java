package webserver.requesthandler;

import java.io.IOException;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class CommentHandler implements RequestHandler {
    @Override
    public void handleGet(HttpRequest request, HttpResponse response) throws IOException {
        setHTMLToBody(response, URLConst.COMMENT_URL);
    }

    @Override
    public void handlePost(HttpRequest request, HttpResponse response) {
        response.setNotFound();
    }
}
