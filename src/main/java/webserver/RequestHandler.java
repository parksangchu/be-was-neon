package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder requestHeaderBuilder = new StringBuilder();
            while (!(line = br.readLine()).isEmpty()) {
                requestHeaderBuilder.append(line).append("\n");
            }
            String requestHeader = requestHeaderBuilder.toString();
            printRequestHeader(requestHeader);

            DataOutputStream dos = new DataOutputStream(out); // 데이터를 담아서 반환
            byte[] body = "<h1>Hello World</h1>".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void printRequestHeader(String requestHeader) {
        logger.debug("requestHeader= {}", requestHeader);

        String requestLine = requestHeader.split("\n")[0];
        logger.debug("requestLine= {}", requestLine);

        String url = requestLine.split(" ")[1];
        logger.debug("url={}", url);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) { // 헤더 정보 입력
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
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
