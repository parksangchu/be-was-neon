package webserver;

import static utils.FileManager.getFileBody;
import static utils.FileManager.getRequestHeader;
import static utils.FileManager.getStaticFilePath;
import static utils.FileManager.getUrl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ContentTypeMapper;
import utils.FileManager;

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
            printRequestHeader(requestHeader);

            String url = getUrl(requestHeader);
            String filePath = getStaticFilePath(url);
            byte[] body = getFileBody(filePath);

            DataOutputStream dos = new DataOutputStream(out); // 데이터를 담아서 반환

            response200Header(dos, body.length, filePath);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void printRequestHeader(String requestHeader) {
        logger.debug("requestHeader= {}", requestHeader);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String filePath) { // 헤더 정보 입력
        try {
            String fileExtension = FileManager.getFileExtension(filePath);
            String contentType = ContentTypeMapper.getContentType(fileExtension);

            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
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
