package utils;

import java.util.UUID;

public class SIDMaker {
    public static String makeSID() {
        return UUID.randomUUID().toString();
    }
}
