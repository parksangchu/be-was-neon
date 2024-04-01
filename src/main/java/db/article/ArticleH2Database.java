package db.article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Article;
import utils.DatabaseConnector;

public class ArticleH2Database implements ArticleDatabase {
    @Override
    public void addArticle(Article article) {
        String sql = "insert into article(userId,content,img) values(?,?,?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, article.getUserId());
            pstmt.setString(2, article.getContent());
            pstmt.setBytes(3, article.getFile());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Article findArticleBySequenceId(Long sequenceId) {
        String sql = "select * from article where id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, sequenceId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String userId = rs.getString("userId");
                    String content = rs.getString("content");
                    byte[] img = rs.getBytes("img");
                    Article article = new Article(userId, content, img);
                    article.setSequenceId(sequenceId);
                    return article;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Article> findAll() {
        String sql = "select * from article";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                List<Article> articles = new ArrayList<>();
                while (rs.next()) {
                    String userId = rs.getString("userId");
                    String content = rs.getString("content");
                    byte[] file = rs.getBytes("img");
                    Article article = new Article(userId, content, file);
                    article.setSequenceId(rs.getLong("id"));
                    articles.add(article);
                }
                return articles;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void clear() {
        String sql = "TRUNCATE TABLE article RESTART IDENTITY";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Long getFirstId() {
        String sql = "select * from article order by id asc limit 1";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id");
                }
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Long getRecentId() {
        String sql = "select * from article order by id desc limit 1";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id");
                }
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
