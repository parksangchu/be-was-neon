package webserver.requesthandler.http.attribute;

import java.util.HashMap;
import java.util.Map;

public class Attributes {
    private final Map<String, Object> store = new HashMap<>();

    public void add(String name, Object object) {
        store.put(name, object);
    }

    public Object get(String name) {
        return store.get(name);
    }

    public boolean isEmpty() {
        return store.isEmpty();
    }
}
