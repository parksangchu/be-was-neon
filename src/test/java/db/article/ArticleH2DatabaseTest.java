package db.article;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import model.Article;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleH2DatabaseTest {

    ArticleDatabase articleDatabase = new ArticleH2Database();
    Article article;

    @BeforeEach
    void setUp() throws IOException {
        articleDatabase.clear();
        article = new Article("sangchu", "content",
                getClass().getResourceAsStream("/치코리타.jpeg").readAllBytes());
        articleDatabase.addArticle(article);
    }

    @Test
    @DisplayName("AID(시퀀스 ID)로 Article을 조회할 수 있다.")
    void findArticleBySequenceId() {
        Article findArticle = articleDatabase.findArticleBySequenceId(1L);
        assertThat(findArticle).isEqualTo(article);
    }

    @Test
    @DisplayName("데이터 베이스의 모든 Article을 조회할 수 있다.")
    void findAll() {
        articleDatabase.addArticle(new Article(null, null, null));
        List<Article> articles = articleDatabase.findAll();
        assertThat(articles.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("데이터베이스의 가장 오래된 id를 조회할 수 있다.")
    void getFirstId() {
        articleDatabase.addArticle(new Article(null, null, null));
        articleDatabase.addArticle(new Article(null, null, null));
        articleDatabase.addArticle(new Article(null, null, null));
        Long firstId = articleDatabase.getFirstId();
        assertThat(firstId).isEqualTo(1L);
    }

    @Test
    @DisplayName("데이터베이스의 가장 최근의 id를 조회할 수 있다.")
    void getRecentId() {
        articleDatabase.addArticle(new Article(null, null, null));
        articleDatabase.addArticle(new Article(null, null, null));
        articleDatabase.addArticle(new Article(null, null, null));
        Long firstId = articleDatabase.getRecentId();
        assertThat(firstId).isEqualTo(4L);
    }

    @AfterAll
    static void afterAll() {
        ArticleDatabase articleDatabase = new ArticleH2Database();
        articleDatabase.clear();
    }
}