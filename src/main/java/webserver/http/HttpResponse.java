package webserver.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private final OutputStream out;
    private HttpStatus status;
    private final Map<String, String> headers;
    private byte[] body;

    public HttpResponse(OutputStream out) {
        this.out = out;
        this.status = HttpStatus.OK;
        this.headers = new HashMap<>();
        this.body = new byte[0];
    }

    public HttpResponse() {
        this.out = null;
        this.headers = new HashMap<>();
        this.status = HttpStatus.OK;
        this.body = new byte[0];
    }

    public void send() throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBytes("HTTP/1.1 " + status.getValue() + " " + status.getReasonPhrase() + "\r\n");
        for (String name : headers.keySet()) {
            dos.writeBytes(name + ": " + headers.get(name) + "\r\n");
        }
        dos.writeBytes("\r\n");
        if (body.length > 0) {
            dos.write(body);
        }
        dos.flush();
    }

    public void setRedirect(String url) {
        setHeader("Location", url);
        setStatus(HttpStatus.FOUND);
    }

    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setBody(byte[] body, ContentType contentType) {
        this.body = body;
        setContentType(contentType);
        setContentLength(body.length);
    }

    private void setContentType(ContentType contentType) {
        headers.put("Content-Type", contentType.getMimeType());
    }

    private void setContentLength(int length) {
        headers.put("Content-Length", String.valueOf(length));
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public byte[] getBody() {
        return body;
    }
}
