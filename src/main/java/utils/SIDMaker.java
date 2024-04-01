package utils;

import java.util.UUID;

/**
 * 세션 ID 생성을 위한 유틸리티 클래스입니다.
 */
public class SIDMaker {
    /**
     * 유니크한 세션 ID를 생성하여 반환합니다.
     *
     * @return 생성된 세션 ID 문자열.
     */
    public static String makeSID() {
        return UUID.randomUUID().toString();
    }
}
