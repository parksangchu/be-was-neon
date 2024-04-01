package model;

import java.util.Arrays;
import java.util.Objects;

/**
 * 웹서버상에 보여줄 Article 객체를 저장하는 클래스
 */
public class Article {
    private String userId;
    private String content;
    private byte[] file;
    private Long sequenceId;

    public Article(String name, String content, byte[] file) {
        this.userId = name;
        this.content = content;
        this.file = file;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Long sequenceId) {
        this.sequenceId = sequenceId;
    }

    @Override
    public String toString() {
        return "Article{" +
                "userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                ", file=" + Arrays.toString(file) +
                ", sequenceId=" + sequenceId +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Article article = (Article) object;
        return Objects.equals(userId, article.userId) && Objects.equals(content, article.content)
                && Arrays.equals(file, article.file);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(userId, content);
        result = 31 * result + Arrays.hashCode(file);
        return result;
    }
}
