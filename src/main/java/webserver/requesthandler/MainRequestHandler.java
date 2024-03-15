package webserver.requesthandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.athenication.Authenticator;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class MainRequestHandler implements Runnable {

    public static final String HOME_URL = "/";
    private static final Logger logger = LoggerFactory.getLogger(MainRequestHandler.class);

    private final Socket connection;
    private final Map<String, RequestHandler> requestHandlers;

    public MainRequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        requestHandlers = initRequestHandlers();
    }

    private Map<String, RequestHandler> initRequestHandlers() {
        Map<String, RequestHandler> requestHandlers = new HashMap<>();
        requestHandlers.put("/", new HomeHandler());
        requestHandlers.put("/create", new UserMaker());
        requestHandlers.put("/certification", new LoginHandler());
        requestHandlers.put("/logout", new LogoutHandler());
        return requestHandlers;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);

            authenticate(request, response); // 인증이 필요한 페이지에 접근하는지 확인
            if (response.isRedirect()) {
                response.send(); // 로그인이 되어있지 않은 상태로 인증이 필요한 페이지에 접근하면 리다이렉트
                return;
            }

            RequestHandler requestHandler = findRequestHandler(request);
            requestHandler.handle(request, response);

            response.send();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void authenticate(HttpRequest request, HttpResponse response) throws IOException {
        Authenticator authenticator = new Authenticator();
        authenticator.authenticate(request, response);
    }

    private RequestHandler findRequestHandler(HttpRequest request) {
        return requestHandlers.getOrDefault(request.getPath(), new StaticResourceFinder());
    }
}
