package webserver.requesthandler.http.message;

import java.util.HashMap;
import java.util.Map;

public class Body {
    private final Map<String, byte[]> datas = new HashMap<>();

    public Body() {
        datas.put("content", new byte[0]);
    }

    public byte[] getContent() {
        return datas.get("content");
    }

    public byte[] getFile() {
        return datas.get("file");
    }

    public String getStringContent() {
        return new String(getContent());
    }

    public void setContent(byte[] content) {
        datas.put("content", content);
    }

    public void setFile(byte[] file) {
        datas.put("file", file);
    }

    public boolean hasEmptyContent() {
        return getContent().length == 0;
    }
}
