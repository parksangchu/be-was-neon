package webserver.requesthandler.bodysetter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.FileManager;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

/**
 * HTML 응답 본문을 설정하는 클래스입니다. HTML 템플릿에 모델 데이터를 적용하여 동적인 웹 페이지를 생성합니다.
 */
public class HtmlSetter {

    public static final Pattern MODEL_PATTERN = Pattern.compile("\\$\\{(.*?)}");

    /**
     * 지정된 뷰 경로에 해당하는 HTML 템플릿을 로드하고, 요청에 포함된 모델 데이터로 내용을 수정하여 응답의 HTML 본문으로 설정합니다.
     *
     * @param request  요청 객체, 모델 데이터를 포함할 수 있습니다.
     * @param response 응답 객체, 수정된 HTML 본문이 설정됩니다.
     * @param viewPath 뷰 파일의 경로.
     * @throws IOException 템플릿 파일을 로드하는 과정에서 발생하는 예외.
     */
    public static void setView(HttpRequest request, HttpResponse response, String viewPath)
            throws IOException {
        byte[] template = FileManager.getTemplate(viewPath);
        if (!request.hasAttribute()) {
            response.setHtmlBody(template); // 모델이 없다면 템플릿 그대로 저장
            return;
        }
        byte[] modifiedHtml = getModifiedHtml(request, template); // 모델이 있다면 수정하여 저장
        response.setHtmlBody(modifiedHtml);
    }

    private static byte[] getModifiedHtml(HttpRequest request, byte[] template) {
        String htmlText = new String(template, StandardCharsets.UTF_8);
        Matcher matcher = MODEL_PATTERN.matcher(htmlText);
        while (matcher.find()) {
            String modelName = matcher.group(1);
            Object model = request.getAttribute(modelName);
            String replaceString = getReplaceString(model); // 모델의 특성에 따른 String 값을 가져온다.
            htmlText = htmlText.replace("${" + modelName + "}", replaceString);
        }
        return htmlText.getBytes();
    }

    private static String getReplaceString(Object attribute) {
        if (attribute instanceof List<?> list) { // 모델이 리스트일 경우
            return generateHtmlForList(list);
        }
        return String.valueOf(attribute);
    }

    private static String generateHtmlForList(List<?> list) {
        StringBuilder sb = new StringBuilder();
        for (Object o : list) { // 모델이 리스트일 경우 모든 요소들을 순회하여 추가한다
            sb.append("<tr>");
            Field[] fields = o.getClass().getDeclaredFields(); // 리플렉션을 이용해 모든 필드를 가져온다
            appendFields(o, fields, sb);
            sb.append("</tr>");
        }
        return sb.toString();
    }

    private static void appendFields(Object o, Field[] fields, StringBuilder sb) {
        try {
            for (Field field : fields) {
                if (!field.getName().equals("password")) { // 패스워드는 민감한 정보이므로 공개하지 않는다
                    field.setAccessible(true);
                    Object fieldValue = field.get(o);
                    sb.append("<td>").append(fieldValue).append("</td>");
                }
            }
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }

    }
}
