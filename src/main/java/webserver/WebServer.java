package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.requesthandler.MainRequestHandler;

/**
 * 웹서버의 메인 클래스입니다. 서버 소켓을 생성하고 클라이언트 연결을 기다린 후, 각 연결에 대해 새로운 스레드에서 요청 처리를 시작합니다.
 */
public class WebServer {
    public static final int DEFAULT_PORT = 8080;
    public static final int THREAD_POOL_SIZE = 80; // 서버 작업은 IOBound 이므로 코어 수의 10배로 설정
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    /**
     * 애플리케이션의 진입점. 사용자 정의 포트 또는 기본 포트에서 서버를 시작합니다.
     *
     * @param args 커맨드라인 인자
     * @throws Exception 서버 시작 중 발생할 수 있는 예외
     */
    public static void main(String[] args) throws Exception {
//        createTestUser();
        int port;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);
            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                executorService.execute(new MainRequestHandler(connection));

            }
        } finally {
            if (!executorService.isShutdown()) {
                executorService.shutdown();
            }
        }
    }
/*
    private static void createTestUser() {
        UserDatabase userDatabase = new UserH2Database();
        if (userDatabase.findAll().isEmpty()) {
            User user = new User("sangchu", "123", "상추", "sangchu@gmail.com");
            userDatabase.addUser(user);
        }
    }*/
}
