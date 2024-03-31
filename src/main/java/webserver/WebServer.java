package webserver;

import db.user.UserDatabase;
import db.user.UserH2Database;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.requesthandler.MainRequestHandler;

public class WebServer {
    public static final int DEFAULT_PORT = 8080;
    public static final int THREAD_POOL_SIZE = 80; // 서버 작업은 IOBound 이므로 코어 수의 10배로 설정
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public static void main(String[] args) throws Exception {
        createTestUser();
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

    private static void createTestUser() {
        UserDatabase userDatabase = new UserH2Database();
        if (userDatabase.findAll().isEmpty()) {
            User user = new User("sangchu", "123", "상추", "sangchu@gmail.com");
            userDatabase.addUser(user);
        }
    }
}
