package webserver.requesthandler.bodysetter;

import java.io.IOException;
import utils.FileManager;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

public class ResponseBodySetter {
    public static void setBody(HttpRequest request, HttpResponse response, String viewPath)
            throws IOException {
        if (viewPath == null) {
            response.setNotFound();
            byte[] body = FileManager.getTemplate("/error/404");
            response.setHtmlBody(body);
            return;
        }
        if (viewPath.startsWith("/static")) {
            StaticResourceSetter.setStaticResource(response, viewPath);
            return;
        }
        if (viewPath.startsWith("redirect:")) {
            String redirectPath = viewPath.split(":")[1];
            response.setRedirect(redirectPath);
            return;
        }
        HtmlSetter.setView(request, response, viewPath);
    }
}
