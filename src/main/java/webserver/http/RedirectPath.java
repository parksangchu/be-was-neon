package webserver.http;

import java.util.ArrayList;
import java.util.List;

public class RedirectPath {
    private static final List<String> store = new ArrayList<>();

    public static void add(String path) {
        store.add(path);
    }

    public static boolean isEmpty() {
        return store.isEmpty();
    }

    public static String getPath() {
        return store.get(0);
    }

    public static void clear() {
        store.clear();
    }
}
