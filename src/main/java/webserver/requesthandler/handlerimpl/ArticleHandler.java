package webserver.requesthandler.handlerimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import webserver.requesthandler.URLConst;
import webserver.requesthandler.http.ContentType;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

public class ArticleHandler implements RequestHandler {
    @Override
    public String handleGet(HttpRequest request, HttpResponse response) throws IOException {
        return URLConst.ARTICLE_URL;
    }

    @Override
    public String handlePost(HttpRequest request, HttpResponse response) throws IOException {
        byte[] file = request.getFile();
        Files.write(Path.of("sangchu.jpeg"), file);
        response.setBody(file, ContentType.JPEG);
        return null;
    }
}
