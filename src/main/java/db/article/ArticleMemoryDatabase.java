package db.article;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import model.Article;

public class ArticleMemoryDatabase implements ArticleDatabase {
    private static final Map<Long, Article> articles = new ConcurrentHashMap<>();
    private static Long sequenceId = 0L;

    public void addArticle(Article article) {
        article.setSequenceId(++sequenceId);
        articles.put(article.getSequenceId(), article);
    }

    public Article findArticleBySequenceId(Long sequenceId) {
        return articles.get(sequenceId);
    }

    public List<Article> findAll() {
        return new ArrayList<>(articles.values());
    }

    public void clear() {
        articles.clear();
        sequenceId = 0L;
    }
}
