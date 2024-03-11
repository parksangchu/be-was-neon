package utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContentTypeMapper {
    private static final Map<String, String> contentTypes = new ConcurrentHashMap<>();

    static {
        contentTypes.put("html", "text/html;charset=utf-8");
        contentTypes.put("css", "text/css");
        contentTypes.put("js", "application/javascript");
        contentTypes.put("png", "image/png");
        contentTypes.put("jpg", "image/jpeg");
        contentTypes.put("jpeg", "image/jpeg");
        contentTypes.put("svg", "image/svg+xml");
    }

    public static String getContentType(String fileExtension) {
        return contentTypes.getOrDefault(fileExtension, "text/html;charset=utf-8");
    }
}
