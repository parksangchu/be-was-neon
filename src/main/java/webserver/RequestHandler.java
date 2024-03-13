package webserver;

import static utils.FileManager.getFileBody;
import static utils.FileManager.getFileExtension;
import static utils.FileManager.getStaticFilePath;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.UserMaker;

public class RequestHandler implements Runnable {

    public static final String CREATE_USER_URL = "/create";
    public static final String HOME_URL = "/";
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);

            if (request.getPath().equals(CREATE_USER_URL)) { // create 요청시 유저 생성 후 홈으로 리다이렉트
                createUser(request, response);
            } else {
                showStaticResource(request, response);
            }
            response.send();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void createUser(HttpRequest request, HttpResponse response) throws IOException {
        UserMaker.createUser(request);
        response.setRedirect(HOME_URL);
    }

    private void showStaticResource(HttpRequest request, HttpResponse response) throws IOException {
        String filePath = getStaticFilePath(request.getPath());
        byte[] body = getFileBody(filePath);
        ContentType contentType = ContentType.getContentTypeByExtension(getFileExtension(filePath));
        response.setBody(body, contentType);
    }

    private void printRequestHeader(Map<String, String> headers) {
        for (String label : headers.keySet()) {
            logger.debug(label + "= {}", headers.get(label));
        }
    }
}
