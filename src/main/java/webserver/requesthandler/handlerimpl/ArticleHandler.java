package webserver.requesthandler.handlerimpl;

import java.io.IOException;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

public class ArticleHandler implements RequestHandler {
    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        return URLConst.ARTICLE_URL;
    }

    @Override
    public String handlePost(HttpRequest request, HttpResponse response) {
        return null;
    }
}
