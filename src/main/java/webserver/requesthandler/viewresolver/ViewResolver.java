package webserver.requesthandler.viewresolver;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.FileManager;
import webserver.requesthandler.http.ContentType;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

public class ViewResolver {

    public static final Pattern MODEL_PATTERN = Pattern.compile("\\$\\{(.*?)}");

    public static void setView(String viewPath, HttpRequest request, HttpResponse response)
            throws IOException, IllegalAccessException {
        if (FileManager.isFile(viewPath)) { // 파일일 경우 정적리소스 반환
            setStaticResource(viewPath, response);
            return;
        }
        setHtml(viewPath, request, response); // 아닐 경우 템플릿 폴더에서 html 찾아서 반환
    }

    private static void setStaticResource(String viewPath, HttpResponse response) throws IOException {
        byte[] staticResource = FileManager.getStaticResource(viewPath);
        String fileExtension = FileManager.getFileExtension(viewPath);
        response.setBody(staticResource, ContentType.getContentTypeByExtension(fileExtension));
    }

    private static void setHtml(String viewPath, HttpRequest request, HttpResponse response)
            throws IOException, IllegalAccessException {
        byte[] template = FileManager.getTemplate(viewPath);
        if (!request.hasAttribute()) {
            response.setHtmlBody(template); // 모델이 없다면 템플릿 그대로 저장
            return;
        }
        byte[] modifiedHtml = getModifiedHtml(request, template); // 모델이 있다면 수정하여 저장
        response.setHtmlBody(modifiedHtml);
    }

    private static byte[] getModifiedHtml(HttpRequest request, byte[] template) throws IllegalAccessException {
        String htmlText = new String(template);
        Matcher matcher = MODEL_PATTERN.matcher(htmlText);
        while (matcher.find()) {
            String modelName = matcher.group(1);
            Object model = request.getAttribute(modelName);
            String replaceString = getReplaceString(model); // 모델의 특성에 따른 String 값을 가져온다.
            htmlText = htmlText.replace("${" + modelName + "}", replaceString);
        }
        return htmlText.getBytes();
    }

    private static String getReplaceString(Object attribute) throws IllegalAccessException {
        if (attribute instanceof List<?> list) { // 모델이 리스트일 경우
            return generateHtmlForList(list);
        }
        return (String) attribute;
    }

    private static String generateHtmlForList(List<?> list) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        for (Object o : list) { // 모델이 리스트일 경우 모든 요소들을 순회하여 추가한다
            sb.append("<tr>");
            Field[] fields = o.getClass().getDeclaredFields();
            appendFields(o, fields, sb);
            sb.append("</tr>");
        }
        return sb.toString();
    }

    private static void appendFields(Object o, Field[] fields, StringBuilder sb) throws IllegalAccessException {
        for (Field field : fields) {
            if (!field.getName().equals("password")) { // 패스워드 공개 X
                field.setAccessible(true);
                Object fieldValue = field.get(o);
                System.out.println(fieldValue);
                sb.append("<td>").append(fieldValue).append("</td>");
            }
        }
    }
}
