package utils;

import db.article.ArticleDatabase;
import db.article.ArticleMemoryDatabase;
import db.user.UserDatabase;
import db.user.UserMemoryDatabase;

/**
 * 의존성 주입을 관리하는 컨테이너 클래스입니다. 사용자와 아티클 데이터베이스 인터페이스의 구현체를 제공합니다.
 */
public class DIContainer {
    /**
     * 사용자 데이터베이스 인터페이스의 구현체를 반환합니다.
     *
     * @return {@link UserDatabase}의 구현체.
     */
    public static UserDatabase getUserDatabase() {
        return new UserMemoryDatabase();
    }

    /**
     * 아티클 데이터베이스 인터페이스의 구현체를 반환합니다.
     *
     * @return {@link ArticleDatabase}의 구현체.
     */
    public static ArticleDatabase getArticleDatabase() {
        return new ArticleMemoryDatabase();
    }
}
