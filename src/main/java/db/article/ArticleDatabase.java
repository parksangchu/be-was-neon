package db.article;

import java.util.List;
import model.Article;

public interface ArticleDatabase {
    void addArticle(Article article);

    Article findArticleBySequenceId(Long sequenceId);

    List<Article> findAll();

    void clear();
}
