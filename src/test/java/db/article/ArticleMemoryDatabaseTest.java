package db.article;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import model.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleMemoryDatabaseTest {
    ArticleDatabase articleDatabase = new ArticleMemoryDatabase();
    Article article;

    @BeforeEach
    void setUp() throws IOException {
        articleDatabase.clear();
        article = new Article("sangchu", "나는 상추입니다.",
                getClass().getResourceAsStream("/치코리타.jpeg").readAllBytes());
        articleDatabase.addArticle(article);
    }

    @Test
    @DisplayName("시퀀스 아이디로 아티클을 조회할 수 있다.")
    void findArticleBySequenceId() throws IOException {
        Article findArticle = articleDatabase.findArticleBySequenceId(1L);
        assertThat(findArticle).isEqualTo(article);

        Article nullArticle = articleDatabase.findArticleBySequenceId(2L);
        assertThat(nullArticle).isNull();

    }

    @Test
    @DisplayName("데이터베이스에 저장된 아티클을 모두 조회할 수 있다.")
    void findAll() throws IOException {
        Article article2 = new Article("sangchu", "나는 상추입니다.", null);
        articleDatabase.addArticle(article2);
        List<Article> all = articleDatabase.findAll();
        assertThat(all.size()).isEqualTo(2);
        assertThat(all).contains(article, article2);
    }
}