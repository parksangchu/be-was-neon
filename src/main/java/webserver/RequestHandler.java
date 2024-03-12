package webserver;

import static utils.FileManager.getFileBody;
import static utils.FileManager.getStaticFilePath;
import static utils.RequestHeaderManager.getRequestHeader;
import static utils.RequestHeaderManager.getUrl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ContentTypeMapper;
import utils.FileManager;
import utils.UserMaker;

public class RequestHandler implements Runnable {

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
            String requestHeader = getRequestHeader(in);
            DataOutputStream dos = new DataOutputStream(out);

            String url = getUrl(requestHeader);
            if (url.startsWith("/create")) {
                UserMaker.createUser(url);
                response302Header(dos, "/");
                return;
            }
            String filePath = getStaticFilePath(url);
            byte[] body = getFileBody(filePath);

            // 데이터를 담아서 반환
            response200Header(dos, body.length, filePath);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void printRequestHeader(String requestHeader) {
        logger.debug("requestHeader= {}", requestHeader);
    }


    private void response302Header(DataOutputStream dos, String redirectUrl) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Found \r\n");
        dos.writeBytes("Location: " + redirectUrl + "\r\n");
        dos.writeBytes("\r\n");
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String filePath)
            throws IOException { // 헤더 정보 입력
        String fileExtension = FileManager.getFileExtension(filePath);
        String contentType = ContentTypeMapper.getContentType(fileExtension);

        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: " + contentType + "\r\n");
        dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        dos.writeBytes("\r\n");
    }

    private void responseBody(DataOutputStream dos, byte[] body) { // 바디 정보 입력
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
