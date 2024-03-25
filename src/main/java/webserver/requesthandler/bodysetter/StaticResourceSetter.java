package webserver.requesthandler.bodysetter;

import java.io.IOException;
import utils.FileManager;
import webserver.requesthandler.http.ContentType;
import webserver.requesthandler.http.HttpResponse;

public class StaticResourceSetter {
    public static void setStaticResource(HttpResponse response, String viewPath) throws IOException {
        byte[] staticResource = FileManager.getStaticResource(viewPath);
        String fileExtension = FileManager.getFileExtension(viewPath);
        response.setBody(staticResource, ContentType.getContentTypeByExtension(fileExtension));
    }
}
