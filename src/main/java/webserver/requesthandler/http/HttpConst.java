package webserver.requesthandler.http;

public interface HttpConst {
    String QUERY_COMMAND_START = "?";
    String PARAMS_DELIMITER = "&";
    String PARAM_DELIMITER = "=";
    String HEADER_COOKIE = "Cookie";
    String HEADER_SET_COOKIE = "Set-Cookie";
    String SET_COOKIE_DELIMITER = "&";
    String HEADER_CONTENT_LENGTH = "Content-Length";
    String HEADER_CONTENT_TYPE = "Content-Type";
    String HEADER_LOCATION = "Location";
    String METHOD_GET = "GET";
    String METHOD_POST = "POST";
    String HEADER_DELIMITER = ":";
    int PARAMS_LENGTH = 2;
    String HTML_FORM_DATA = "application/x-www-form-urlencoded";
    String MULTIPART_FORM_DATA = "multipart/form-data";
    String HTTP_VERSION = "HTTP/1.1 ";
    String CRLF = "\r\n";
    String START_LINE_DELIMITER = " ";
    String COOKIE_VALUE_DELIMITER = ";";
    String SPACE_REGEX = "\\s";

}
