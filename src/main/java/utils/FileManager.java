package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * 파일과 관련된 유틸리티 메서드를 제공하는 클래스입니다. 정적 리소스와 템플릿 파일을 로드하는 기능을 포함합니다.
 */
public class FileManager {
    public static final String FILE_EXTENSION_MARKER = ".";
    public static final String FILE_SEPARATOR = "/";
    public static final String NO_FILE_EXTENSION = "";
    public static final String TEMPLATES_DIRECTORY = "/templates";
    public static final String STATIC_DIRECTORY = "/static";
    public static final String INDEX_HTML_FILE = "/index.html";

    /**
     * 주어진 요청 경로가 파일인지 여부를 확인합니다.
     *
     * @param requestPath 요청받은 경로.
     * @return 파일인 경우 true, 아니면 false.
     */
    public static boolean isFile(String requestPath) {
        return requestPath.contains(FILE_EXTENSION_MARKER);
    }

    /**
     * 주어진 요청 경로에서 파일 확장자를 추출합니다.
     *
     * @param requestPath 요청받은 경로.
     * @return 파일 확장자. 확장자가 없는 경우 빈 문자열을 반환합니다.
     */
    public static String getFileExtension(String requestPath) {
        int lastIndexOfDot = requestPath.lastIndexOf(FILE_EXTENSION_MARKER);
        int lastIndexOfSlash = requestPath.lastIndexOf(FILE_SEPARATOR);

        if (lastIndexOfDot == -1 || lastIndexOfDot < lastIndexOfSlash) {
            return NO_FILE_EXTENSION; // 확장자가 없음을 나타냅니다.
        }

        return requestPath.substring(lastIndexOfDot + 1);
    }

    /**
     * 정적 리소스 파일의 바이트 배열을 로드합니다.
     *
     * @param requestPath 요청받은 경로.
     * @return 파일의 바이트 배열. 파일이 없는 경우 null을 반환합니다.
     * @throws IOException 파일을 읽는 과정에서 오류가 발생한 경우.
     */
    public static byte[] getStaticResource(String requestPath) throws IOException {
        InputStream resourceAsStream = FileManager.class.getResourceAsStream(STATIC_DIRECTORY + requestPath);
        if (resourceAsStream == null) {
            return null;
        }
        return resourceAsStream.readAllBytes();
    }

    /**
     * 템플릿 파일의 바이트 배열을 로드합니다.
     *
     * @param requestPath 요청받은 경로.
     * @return 템플릿 파일의 바이트 배열.
     * @throws IOException 파일을 읽는 과정에서 오류가 발생한 경우.
     */
    public static byte[] getTemplate(String requestPath) throws IOException {
        return Objects.requireNonNull(
                        FileManager.class.getResourceAsStream(TEMPLATES_DIRECTORY + requestPath + INDEX_HTML_FILE))
                .readAllBytes();
    }
}
