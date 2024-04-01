package webserver.requesthandler.httpParser;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * BufferedInputStream에서 데이터를 읽는 유틸리티 클래스입니다.
 */
public class BISReader {
    /**
     * 주어진 BufferedInputStream에서 한 줄을 읽어 문자열로 반환합니다.
     *
     * @param bis 입력 스트림
     * @return 읽은 한 줄의 문자열
     * @throws IOException 입출력 오류가 발생한 경우
     */
    public static String readLine(BufferedInputStream bis) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int b;
        while ((b = bis.read()) != -1) {
            if (b == '\r') {
                bis.mark(1); // 다음 문자가 '\n'인지 확인하기 위해 마크
                int next = bis.read();
                if (next != '\n') {
                    bis.reset(); // '\n'이 아니면 스트림 위치를 '\r' 다음으로 리셋
                }
                break; // 줄의 끝
            } else {
                buffer.write(b);
            }
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }
}
