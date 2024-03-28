package webserver.requesthandler.http;

import java.net.HttpCookie;
import java.util.Map;
import webserver.requesthandler.http.attribute.Attributes;
import webserver.requesthandler.http.message.Body;
import webserver.requesthandler.http.message.Headers;
import webserver.requesthandler.http.message.Parameters;
import webserver.requesthandler.http.message.RequestLine;

public class HttpRequest {
    private RequestLine requestLine;
    private Headers headers;
    private Body body;
    private Parameters parameters;
    private Attributes attributes;

    public HttpRequest(RequestLine requestLine, Headers headers, Body body, Parameters parameters) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
        this.parameters = parameters;
        this.attributes = new Attributes();
    }

    public HttpRequest() {
        requestLine = new RequestLine();
        headers = new Headers();
        body = new Body();
        parameters = new Parameters();
        attributes = new Attributes();
    }


    public boolean isGET() {
        return requestLine.isGET();
    }

    public boolean isPOST() {
        return requestLine.isPOST();
    }

    public boolean hasAttribute() {
        return !attributes.isEmpty();
    }

    public HttpCookie getCookie(String cookieName) {
        return headers.getCookie(cookieName);
    }

    public String getHeader(String headerKey) {
        return headers.get(headerKey);
    }

    public String getContentType() {
        return headers.getContentType();
    }

    public String getParameter(String paramKey) {
        return parameters.get(paramKey);
    }

    public String getMethod() {
        return requestLine.getMethod();
    }

    public String getURL() {
        return requestLine.getURL();
    }

    public byte[] getBody() {
        return body.getContent();
    }

    public String getStringBody() {
        return body.getStringContent();
    }

    public byte[] getFile() {
        return body.getFile();
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void setURL(String URL) {
        requestLine.setURL(URL);
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = new Headers(headers);
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setBody(String body) {
        this.body.setContent(body.getBytes());
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = new Parameters(parameters);
    }

    public void setAttribute(String name, Object object) {
        attributes.add(name, object);
    }
}
