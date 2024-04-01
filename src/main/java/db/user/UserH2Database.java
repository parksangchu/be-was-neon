package db.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;
import utils.DatabaseConnector;

public class UserH2Database implements UserDatabase {
    @Override
    public void addUser(User user) {
        String sql = "insert into \"user\"(loginId, name, password, email) values(?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // 수정된 부분
            pstmt.setString(1, user.getLoginId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getEmail());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }


    @Override
    public User findUserByLoginId(String loginId) {
        String sql = "select * from \"user\" where loginId = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, loginId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    long sequenceId = rs.getLong("id");
                    String name = rs.getString("name");
                    String password = rs.getString("password");
                    String email = rs.getString("email");

                    User user = new User(loginId, password, name, email);
                    user.setSequenceId(sequenceId);
                    return user;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from \"user\"";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    String loginId = rs.getString("loginId");
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    User user = new User(loginId, password, name, email);
                    user.setSequenceId(rs.getLong("id"));
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void clear() {
        String sql = "truncate table \"user\" restart identity";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
