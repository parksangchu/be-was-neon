package utils;

import db.article.ArticleDatabase;
import db.article.ArticleMemoryDatabase;
import db.user.UserDatabase;
import db.user.UserH2Database;

public class DIContainer {

    public static UserDatabase getUserDatabase() {
        return new UserH2Database();
    }

    public static ArticleDatabase getArticleDatabase() {
        return new ArticleMemoryDatabase();
    }
}
