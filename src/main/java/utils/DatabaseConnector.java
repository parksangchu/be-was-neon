package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 데이터베이스 연결을 관리하는 유틸리티 클래스입니다. H2 데이터베이스에 연결하기 위한 정보를 제공합니다.
 */
public class DatabaseConnector {
    private static final String JDBC_URL = "jdbc:h2:tcp://localhost/~/was";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    /**
     * 데이터베이스 연결을 생성하고 반환합니다.
     *
     * @return 데이터베이스와의 연결을 나타내는 {@link Connection} 객체.
     * @throws SQLException 데이터베이스 연결 과정에서 오류가 발생한 경우.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }
}
