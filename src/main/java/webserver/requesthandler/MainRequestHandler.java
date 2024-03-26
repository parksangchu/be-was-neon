package webserver.requesthandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.requesthandler.authenticator.Authenticator;
import webserver.requesthandler.authenticator.UnauthenticatedURLs;
import webserver.requesthandler.bodysetter.ResponseBodySetter;
import webserver.requesthandler.handlercommander.RequestHandlerCommander;
import webserver.requesthandler.handlerimpl.ArticleHandler;
import webserver.requesthandler.handlerimpl.RequestHandler;
import webserver.requesthandler.handlermapper.RequestHandlerMapper;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;
import webserver.requesthandler.httpParser.HttpRequestParser;
import webserver.requesthandler.responewriter.HttpResponseWriter;

public class MainRequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(MainRequestHandler.class);

    private final Socket connection;
    private final Authenticator authenticator;

    public MainRequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        authenticator = new Authenticator(new UnauthenticatedURLs());
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest request = HttpRequestParser.parse(in);
            HttpResponse response = new HttpResponse();
            HttpResponseWriter responseWriter = new HttpResponseWriter(out);

            boolean isAuthenticated = authenticator.isAuthenticated(request, response);// 인증이 필요한 페이지에 접근하는지 확인

            if (!isAuthenticated) {
                responseWriter.send(response);
                return;
            }

            RequestHandler requestHandler = RequestHandlerMapper.findRequestHandler(request); // 매핑정보와 일치하는 서브핸들러 찾기
            String viewPath = RequestHandlerCommander.execute(requestHandler, request,
                    response); // 메서드에 따라 동작 실행하여 사용자에게 보여줄 뷰 경로 반환

            if (requestHandler instanceof ArticleHandler && request.isPOST()) {
                responseWriter.send(response);
                return;
            }

            ResponseBodySetter.setBody(request, response, viewPath); // 뷰 경로에 따라 responseBody 설정
            responseWriter.send(response);

        } catch (IOException | IllegalArgumentException e) {
            logger.error(e.getMessage());
        }
    }
}
