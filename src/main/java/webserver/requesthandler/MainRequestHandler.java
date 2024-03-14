package webserver.requesthandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class MainRequestHandler implements Runnable {

    public static final String HOME_URL = "/";
    private static final Logger logger = LoggerFactory.getLogger(MainRequestHandler.class);

    private final Socket connection;
    private final Map<String, RequestHandler> requestHandlers;

    public MainRequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        requestHandlers = new HashMap<>();
        requestHandlers.put("/", new HomeHandler());
        requestHandlers.put("/create", new UserMaker());
        requestHandlers.put("/certification", new LoginHandler());
        requestHandlers.put("/logout", new LogoutHandler());
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);

            RequestHandler requestHandler = findRequestHandler(request);

            requestHandler.handle(request, response);
            response.send();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private RequestHandler findRequestHandler(HttpRequest request) {
        return requestHandlers.getOrDefault(request.getPath(), new StaticResourceFinder());
    }
}
